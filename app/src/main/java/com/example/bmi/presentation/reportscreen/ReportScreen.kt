package com.example.bmi.presentation.reportscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ReportScreen(
    height: String,
    weight: String
) {
    val state = BMICalculator.calculateBMI(height = height, weight = weight)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = state.resultImage),
                contentDescription = null,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .width(200.dp)
                    .height(200.dp)
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 24.dp
                    )
            )

            Text(
                text = state.resultTv,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 24.dp
                    )
            )

            Text(
                text = state.resultAdvice,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 24.dp
                    )
            )
        }
    }
}
