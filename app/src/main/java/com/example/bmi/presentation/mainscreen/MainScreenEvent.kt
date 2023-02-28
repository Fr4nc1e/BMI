package com.example.bmi.presentation.mainscreen

sealed class MainScreenEvent {
    data class EnterHeight(val height: String) : MainScreenEvent()
    data class EnterFeet(val feet: String) : MainScreenEvent()
    data class EnterInch(val inch: String) : MainScreenEvent()
    data class EnterWeight(val weight: String) : MainScreenEvent()
    object ShowDialog : MainScreenEvent()
    object Report : MainScreenEvent()
}
