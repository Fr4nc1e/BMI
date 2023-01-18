package com.example.bmi.presentation.component

import androidx.annotation.StringRes

sealed class UiEvent {
    data class SnackBarEvent(@StringRes val text: Int) : UiEvent()
}
