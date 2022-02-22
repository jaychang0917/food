package com.jaychang.food.feature.phonenumber.internal

import com.jaychang.food.api.models.Country

internal data class PhoneNumberUiState(
    val selectedCountry: Country? = null,
    val isLoadingBarVisible: Boolean = false,
    val isNextButtonVisible: Boolean = false,
    val isNextButtonEnabled: Boolean = false,
    val isErrorViewVisible: Boolean = false,
    val navigation: Navigation? = null
)