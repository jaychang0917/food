package com.jaychang.food.feature.password.internal

internal data class PasswordUiState(
    val navigation: Navigation? = null,
    val isLoadingBarVisible: Boolean = false,
    val isNextButtonVisible: Boolean = false,
    val isNextButtonEnabled: Boolean = false,
    val isErrorViewVisible: Boolean = false
)