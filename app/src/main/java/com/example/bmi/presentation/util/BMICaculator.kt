package com.example.bmi.presentation.reportscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.bmi.R
import java.text.DecimalFormat

object BMICalculator {

    @Composable
    fun calculateBMI(height: String, weight: String): ReportState {
        val state = ReportState()
        val sHeight = height.toDouble() / 100
        val sWeight = weight.toDouble()
        val bmi = sWeight / sHeight / sHeight
        val nf = DecimalFormat("0.00")
        state.resultTv = stringResource(id = R.string.bmi_result) + " " + nf.format(bmi)
        if (bmi > 25) {
            state.apply {
                resultImage = R.drawable.bot_fat
                resultAdvice = stringResource(id = R.string.advice_heavy)
            }
        } else if (bmi < 20) {
            state.apply {
                resultImage = R.drawable.bot_thin
                resultAdvice = stringResource(id = R.string.advice_light)
            }
        } else {
            state.apply {
                resultImage = R.drawable.bot_fit
                resultAdvice = stringResource(id = R.string.advice_average)
            }
        }

        return state
    }
}
