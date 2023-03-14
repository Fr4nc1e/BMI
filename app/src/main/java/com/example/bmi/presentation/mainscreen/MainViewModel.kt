package com.example.bmi.presentation.mainscreen

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bmi.presentation.component.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _heightState = mutableStateOf(MainScreenState())
    val heightState: State<MainScreenState> = _heightState
    private val _feetState = mutableStateOf("")
    val feetState: State<String> = _feetState
    private val _inchState = mutableStateOf("")
    val inchState: State<String> = _inchState
    private val _weightState = mutableStateOf(MainScreenState())
    val weightState: State<MainScreenState> = _weightState
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    private val _showDialogState = mutableStateOf(false)
    val showDialogState: State<Boolean> = _showDialogState

    init {
        _heightState.value = _heightState.value.copy(
            inputText = sharedPreferences.getString("height", "0")!!
        )

        _weightState.value = _weightState.value.copy(
            inputText = sharedPreferences.getString("weight", "0")!!
        )

        _feetState.value = sharedPreferences.getString("feet", "0")!!

        _inchState.value = sharedPreferences.getString("inch", "0")!!
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
            MainScreenEvent.ShowDialog -> {
                _showDialogState.value = !showDialogState.value
            }

            is MainScreenEvent.EnterFeet -> {
                _feetState.value = event.feet
            }
            is MainScreenEvent.EnterInch -> {
                _inchState.value = event.inch
            }
        }
    }

    private fun validation() {
//        if (!DataValidator.validate(
//                height = heightState.value.inputText,
//                weight = weightState.value.inputText
//            )
//        ) {
//            viewModelScope.launch {
//                _eventFlow.emit(
//                    UiEvent.SnackBarEvent(R.string.bmi_warning)
//                )
//            }
//        } else {
//            sharedPreferences.edit()
//                .putString("height", heightState.value.inputText).apply()
//            sharedPreferences.edit()
//                .putString("weight", weightState.value.inputText).apply()
//        }
        sharedPreferences.edit().putString("weight", _weightState.value.inputText).apply()
        sharedPreferences.edit().putString("feet", _feetState.value).apply()
        sharedPreferences.edit().putString("inch", _inchState.value).apply()
    }
}
