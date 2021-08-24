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

package com.jaychang.food.feature.country.internal.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaychang.food.api.models.Country
import com.jaychang.food.core.arch.Event
import com.jaychang.food.feature.country.internal.item.CountryItemViewModel

class CountryListViewModel(allCountries: List<Country>, selectedCountry: Country) {
    val onItemClicked = MutableLiveData<Event<CountryItemViewModel>>()

    private val allCountryItems: List<CountryItemViewModel> = allCountries.map {
        CountryItemViewModel(it, selectedCountry, onItemClicked)
    }

    private val _headerVisible = MutableLiveData(true)
    val headerVisible: LiveData<Boolean> = _headerVisible

    private val _countryItems = MutableLiveData<List<CountryItemViewModel>>()
    val countryItems: LiveData<List<CountryItemViewModel>> = _countryItems

    init {
        updateItems(allCountryItems)
    }

    fun matched(keyword: String): List<CountryItemViewModel> {
        return allCountryItems.filter {
            it.country.name.startsWith(keyword, ignoreCase = true)
        }
    }

    fun updateItems(items: List<CountryItemViewModel>) {
        _countryItems.value = items
    }

    fun setSearchMode(isInSearchMode: Boolean) {
        _headerVisible.value = !isInSearchMode
    }

    fun reset() {
        updateItems(allCountryItems)
    }
}
