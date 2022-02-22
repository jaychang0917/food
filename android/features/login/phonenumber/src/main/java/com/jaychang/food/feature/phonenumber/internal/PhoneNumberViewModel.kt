/*
 * Copyright (C) 2021. Jay Chang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.jaychang.food.feature.phonenumber.internal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaychang.food.api.models.Country
import com.jaychang.food.data.UserExistResult
import com.jaychang.food.data.UserRepository
import com.jaychang.food.core.subscribeResult
import com.jaychang.food.core.util.update
import com.jaychang.food.data.CountryRepository
import com.jaychang.food.feature.country.OnCountrySelectListener
import com.jaychang.food.feature.flow.login.LoginFlowContext
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
internal class PhoneNumberViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val countryRepository: CountryRepository
) : ViewModel(), OnCountrySelectListener {
    lateinit var loginFlowContext: LoginFlowContext

    private val disposables = CompositeDisposable()

    private val _uiState = MutableLiveData(PhoneNumberUiState())
    val uiState: LiveData<PhoneNumberUiState> = _uiState

    init {
        countryRepository.default()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeResult { country ->
                updateDefaultCountry(country)
            }
            .addTo(disposables)

        countryRepository.defaultCountry
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeResult { country ->
                updateDefaultCountry(country)
            }
            .addTo(disposables)
    }

    private fun updateDefaultCountry(country: Country) {
        _uiState.update {
            it.copy(selectedCountry = country)
        }
    }

    fun onCountryFlagIconClicked() {
        _uiState.update {
            it.copy(navigation = Navigation.CountrySelection(it.selectedCountry!!))
        }
    }

    fun onPhoneNumberChanged(phoneNumber: String) {
        _uiState.update {
            it.copy(
                isErrorViewVisible = false,
                isNextButtonVisible = phoneNumber.isNotEmpty(),
                isNextButtonEnabled = phoneNumber.isNotEmpty()
            )
        }
    }

    fun onNextButtonClicked(phoneNumber: String) {
        _uiState.update {
            it.copy(
                isLoadingBarVisible = true,
                isNextButtonEnabled = false
            )
        }

        userRepository.exist(phoneNumber)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeResult { result ->
                _uiState.update {
                    it.copy(
                        isLoadingBarVisible = false,
                        isNextButtonEnabled = true
                    )
                }

                when (result) {
                    is UserExistResult.Success -> {
                        loginFlowContext.data.phoneNumber = phoneNumber
                        _uiState.update {
                            it.copy(
                                isErrorViewVisible = false,
                                navigation = Navigation.Password(phoneNumber)
                            )
                        }
                    }
                    UserExistResult.Error.InvalidPhoneNumber -> {
                        _uiState.update {
                            it.copy(isErrorViewVisible = true)
                        }
                    }
                }
            }
            .addTo(disposables)
    }

    fun close() {
        _uiState.update {
            it.copy(navigation = Navigation.Close())
        }
    }

    override fun onCountrySelected(country: Country) {
        _uiState.update {
            it.copy(selectedCountry = country)
        }
    }

    override fun onCleared() {
        disposables.clear()
    }
}
