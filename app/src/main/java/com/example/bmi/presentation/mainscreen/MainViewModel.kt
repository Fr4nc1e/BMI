package com.example.bmi.presentation.mainscreen

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bmi.R
import com.example.bmi.presentation.component.UiEvent
import com.example.bmi.presentation.util.DataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _heightState = mutableStateOf(MainScreenState())
    val heightState: State<MainScreenState> = _heightState
    private val _weightState = mutableStateOf(MainScreenState())
    val weightState: State<MainScreenState> = _weightState
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        _heightState.value = _heightState.value.copy(
            inputText = sharedPreferences.getString("height", "0")!!
        )

        _weightState.value = _weightState.value.copy(
            inputText = sharedPreferences.getString("weight", "0")!!
        )
    }

    fun onEvent(
        event: MainScreenEvent
    ) {
        when (event) {
            is MainScreenEvent.EnterHeight -> {
                _heightState.value = _heightState.value.copy(
                    inputText = event.height
                )
            }
            is MainScreenEvent.EnterWeight -> {
                _weightState.value = _weightState.value.copy(
                    inputText = event.weight
                )
            }
            MainScreenEvent.Report -> {
                validation()
            }
        }
    }

    private fun validation() {
        if (!DataValidator.validate(
                height = heightState.value.inputText,
                weight = weightState.value.inputText
            )
        ) {
            viewModelScope.launch {
                _eventFlow.emit(
                    UiEvent.SnackBarEvent(R.string.bmi_warning)
                )
            }
        } else {
            sharedPreferences.edit()
                .putString("height", heightState.value.inputText).apply()
            sharedPreferences.edit()
                .putString("weight", weightState.value.inputText).apply()
        }
    }
}
