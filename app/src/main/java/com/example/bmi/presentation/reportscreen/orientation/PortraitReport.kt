package com.example.bmi.presentation.reportscreen.orientation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bmi.R
import com.example.bmi.presentation.reportscreen.ReportState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PortraitReport(
    state: ReportState
) {
    val scale = remember {
        Animatable(1f)
    }

    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.Main) {
            when (state.resultImage) {
                R.drawable.bot_thin -> {
                    scale.animateTo(
                        targetValue = 0.5f,
                        animationSpec = tween(durationMillis = 4000)
                    )
                }
                R.drawable.bot_fit -> {}
                R.drawable.bot_fat -> {
                    scale.animateTo(
                        targetValue = 1.5f,
                        animationSpec = tween(durationMillis = 4000)
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = state.resultImage),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(200.dp)
                    .height(200.dp)
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 24.dp
                    )
                    .scale(scale.value)
            )

            Text(
                text = state.resultTv,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
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
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 24.dp
                    )
            )
        }
    }
}
