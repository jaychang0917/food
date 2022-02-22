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

package com.jaychang.food.feature.country.internal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaychang.food.api.models.Country
import com.jaychang.food.core.subscribeResult
import com.jaychang.food.core.util.update
import com.jaychang.food.data.CountryRepository
import com.jaychang.food.feature.country.CountrySelectionArgs
import com.jaychang.food.feature.country.internal.item.CountryItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
internal class CountrySelectionViewModel @Inject constructor(
    private val countryRepository: CountryRepository,
) : ViewModel() {
    private lateinit var args: CountrySelectionArgs
    private val disposables = CompositeDisposable()

    private var allCountries: List<Country> = emptyList()
    private var selectedCountry: Country? = null

    private val _uiState = MutableLiveData(CountrySelectionUiState())
    val uiState: LiveData<CountrySelectionUiState> = _uiState

    fun init(args: CountrySelectionArgs) {
        this.args = args
        updateInitialState()
    }

    private fun updateInitialState() {
        val allRequest = countryRepository.all()
        val selectedRequest = countryRepository.default()

        Single.zip(allRequest, selectedRequest, { countries, selectedCountry -> countries to selectedCountry })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeResult {
                allCountries = it.first
                selectedCountry = it.second
                _uiState.update { state ->
                    state.copy(
                        countryListState = mapCountryList(allCountries, selectedCountry!!),
                        selectedCountryState = mapCountryItem(selectedCountry!!, selectedCountry!!)
                    )
                }
            }
            .addTo(disposables)
    }

    private fun mapCountryList(items: List<Country>, selected: Country): List<CountryItemUiState> {
        return items.map { mapCountryItem(it, selected) }
    }

    private fun mapCountryItem(country: Country, selected: Country): CountryItemUiState {
        return CountryItemUiState(
            flag = country.imageData,
            name = country.name,
            isSelected = country == selected,
            onItemClicked = { selectedCountry(country) }
        )
    }

    private fun selectedCountry(country: Country) {
        countryRepository.updateDefaultCountry(country)
        _uiState.update {
            it.copy(navigation = Navigation.Close())
        }
        args.onCountrySelectListener.onCountrySelected(country)
    }

    fun navigateUp() {
        _uiState.update {
            it.copy(navigation = Navigation.Close())
        }
    }

    fun onSearchKeywordChanged(keyword: String) {
        val matched = allCountries.filter {
            it.name.lowercase().startsWith(keyword.lowercase())
        }
        val hasResult = matched.isNotEmpty()
        val isInSearchMode = keyword.isNotEmpty()

        val updatedListState = if (isInSearchMode) {
            mapCountryList(matched, selectedCountry!!)
        } else {
            mapCountryList(allCountries, selectedCountry!!)
        }

        _uiState.update {
            it.copy(
                countryListState = updatedListState,
                isListSectionHeaderVisible = !isInSearchMode,
                isSelectedCountryHeaderVisible = !isInSearchMode,
                isEmptyResultVisible = !hasResult,
                searchKeyword = keyword
            )
        }
    }

    override fun onCleared() {
        disposables.clear()
    }
}
