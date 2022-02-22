package com.jaychang.food.feature.country.internal.item

internal data class CountryItemUiState(
    val flag: String? = null,
    val name: String? = null,
    val isSelected: Boolean = false,
    val onItemClicked: () -> Unit = {}
)