package com.jaychang.food.feature.country.internal

import com.jaychang.food.feature.country.internal.item.CountryItemUiState

internal data class CountrySelectionUiState(
    val selectedCountryState: CountryItemUiState? = null,
    val countryListState: List<CountryItemUiState>? = null,
    val isSelectedCountryHeaderVisible: Boolean = false,
    val isEmptyResultVisible: Boolean = false,
    val searchKeyword: String? = null,
    val isListSectionHeaderVisible: Boolean = false,
    val navigation: Navigation? = null
)