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
import com.jaychang.food.core.arch.Event
import com.jaychang.food.feature.country.internal.item.CountryItemViewModel
import com.jaychang.food.feature.country.internal.list.CountryListViewModel
import com.jaychang.food.data.CountryRepository
import com.jaychang.food.feature.country.CountrySelectionArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
internal class CountrySelectionViewModel @Inject constructor(
    private val countryRepository: CountryRepository
) : ViewModel() {
    private lateinit var args: CountrySelectionArgs
    private val disposables = CompositeDisposable()

    lateinit var countryListViewModel: CountryListViewModel
    val onCountryItemClicked: LiveData<Event<CountryItemViewModel>> get() = countryListViewModel.onItemClicked

    private val _navigation = MutableLiveData<Navigation>()
    val navigation: LiveData<Navigation> = _navigation

    private val _selected = MutableLiveData<CountryItemViewModel>()
    val selected: LiveData<CountryItemViewModel> = _selected

    private val _isEmptyResultVisible = MutableLiveData<Boolean>()
    val isEmptyResultVisible: LiveData<Boolean> = _isEmptyResultVisible

    private val _isSelectedCountryHeaderVisible = MutableLiveData<Boolean>()
    val isSelectedCountryHeaderVisible: LiveData<Boolean> = _isSelectedCountryHeaderVisible

    private val _searchKeyword = MutableLiveData<String>()
    val searchKeyword: LiveData<String> = _searchKeyword

    fun init(args: CountrySelectionArgs) {
        this.args = args
        val selectedCountry = args.selectedCountry

        countryListViewModel = CountryListViewModel(countryRepository.all().blockingGet(), selectedCountry)

        _selected.value = CountryItemViewModel(selectedCountry, selectedCountry, countryListViewModel.onItemClicked)
    }

    fun navigateUp() {
        _navigation.value = Navigation.Close()
    }

    fun onSearchKeywordChanged(keyword: String) {
        _searchKeyword.value = keyword

        val matched = countryListViewModel.matched(keyword)
        val hasResult = matched.isNotEmpty()
        val isInSearchMode = keyword.isNotEmpty()
        countryListViewModel.setSearchMode(isInSearchMode)
        _isSelectedCountryHeaderVisible.value = !isInSearchMode

        _isEmptyResultVisible.value = !hasResult

        if (isInSearchMode) {
            countryListViewModel.updateItems(matched)
        } else {
            countryListViewModel.reset()
        }
    }

    fun selectedCountry(country: Country) {
        countryRepository.setDefaultCountry(country)
        args.onCountrySelectListener.onSelected(country)
    }

    override fun onCleared() {
        disposables.clear()
    }
}
