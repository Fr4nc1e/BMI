package com.example.bmi.presentation.util

object DataValidator {
    fun validate(height: String, weight: String): Boolean {
        if (height.isBlank() ||
            weight.isBlank() ||
            height == "0" ||
            weight == "0"
        ) {
            return false
        }
        return true
    }
}
