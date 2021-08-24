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

    private val _navigation = MutableLiveData<Navigation>()
    val navigation: LiveData<Navigation> = _navigation

    private val _isLoadingBarVisible = MutableLiveData(false)
    val isLoadingBarVisible: LiveData<Boolean> = _isLoadingBarVisible

    private val _isNextButtonVisible = MutableLiveData(false)
    val isNextButtonVisible: LiveData<Boolean> = _isNextButtonVisible

    private val _isNextButtonEnabled = MutableLiveData(false)
    val isNextButtonEnabled: LiveData<Boolean> = _isNextButtonEnabled

    private val _isErrorViewVisible = MutableLiveData(false)
    val isErrorViewVisible: LiveData<Boolean> = _isErrorViewVisible

    private val _selectedCountry = MutableLiveData<Country?>()
    val selectedCountry: LiveData<Country?> = _selectedCountry

    init {
        countryRepository.default()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeResult {
                _selectedCountry.value = it
            }
            .addTo(disposables)
    }

    fun onCountryFlagIconClicked() {
        _navigation.value = Navigation.CountrySelection(selectedCountry.value!!)
    }

    fun onPhoneNumberChanged(phoneNumber: String) {
        _isErrorViewVisible.value = false
        _isNextButtonVisible.value = phoneNumber.isNotEmpty()
        _isNextButtonEnabled.value = phoneNumber.isNotEmpty()
    }

    fun onNextButtonClicked(phoneNumber: String) {
        _isLoadingBarVisible.value = true
        _isNextButtonEnabled.value = false

        userRepository.exist(phoneNumber)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeResult {
                _isLoadingBarVisible.value = false
                _isNextButtonEnabled.value = true

                when (it) {
                    is UserExistResult.Success -> {
                        _isErrorViewVisible.value = false
                        loginFlowContext.data.phoneNumber = phoneNumber
                        _navigation.value = Navigation.Password(phoneNumber)
                    }
                    UserExistResult.Error.InvalidPhoneNumber -> {
                        _isErrorViewVisible.value = true
                    }
                }
            }
            .addTo(disposables)
    }

    fun close() {
        _navigation.value = Navigation.Close()
    }

    override fun onSelected(country: Country) {
        _selectedCountry.value = country
    }

    override fun onCleared() {
        disposables.clear()
    }
}
