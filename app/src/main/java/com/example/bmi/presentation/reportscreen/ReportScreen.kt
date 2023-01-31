package com.example.bmi.presentation.reportscreen

import android.content.res.Configuration
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.platform.LocalConfiguration
import com.example.bmi.presentation.reportscreen.orientation.LandscapeReport
import com.example.bmi.presentation.reportscreen.orientation.PortraitReport
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ReportScreen(
    height: String,
    weight: String
) {
    val state = BMICalculator.calculateBMI(height = height, weight = weight)
    var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }
    val configuration = LocalConfiguration.current

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LandscapeReport(state)
        }
        Configuration.ORIENTATION_PORTRAIT -> {
            PortraitReport(state)
        }
    }
}
