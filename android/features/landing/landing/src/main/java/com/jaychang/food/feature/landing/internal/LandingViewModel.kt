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

package com.jaychang.food.feature.landing.internal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaychang.food.api.models.Country
import com.jaychang.food.core.subscribeResult
import com.jaychang.food.core.util.update
import com.jaychang.food.data.CountryRepository
import com.jaychang.food.feature.country.OnCountrySelectListener
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
internal class LandingViewModel @Inject constructor(
    countryRepository: CountryRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()

    private val _uiState = MutableLiveData(LandingUiState())
    val uiState: LiveData<LandingUiState> = _uiState

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

    fun onPhoneTextFieldClicked() {
        _uiState.update {
            it.copy(navigation = Navigation.LoginPhone())
        }
    }

    override fun onCleared() {
        disposables.clear()
    }
}
