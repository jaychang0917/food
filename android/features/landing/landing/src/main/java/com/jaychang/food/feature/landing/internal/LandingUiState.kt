package com.jaychang.food.feature.landing.internal

import com.jaychang.food.api.models.Country

internal data class LandingUiState(
    val selectedCountry: Country? = null,
    val navigation: Navigation? = null
)