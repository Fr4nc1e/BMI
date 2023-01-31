package com.example.bmi.presentation.reportscreen.orientation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bmi.presentation.reportscreen.ReportState

@Composable
fun LandscapeReport(
    state: ReportState
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = state.resultImage),
                contentDescription = null,
                modifier = Modifier
                    .align(CenterVertically)
                    .width(200.dp)
                    .height(200.dp)
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 24.dp
                    )
            )

            Column(modifier = Modifier.align(CenterVertically)) {
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
}
