package com.example.bmi.presentation.mainscreen.orientation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bmi.R
import com.example.bmi.presentation.destinations.ReportScreenDestination
import com.example.bmi.presentation.mainscreen.MainScreenEvent
import com.example.bmi.presentation.mainscreen.MainScreenState
import com.example.bmi.presentation.mainscreen.MainViewModel
import com.example.bmi.presentation.util.DataValidator
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun PortraitContent(
    height: MainScreenState,
    weight: MainScreenState,
    viewModel: MainViewModel,
    navigator: DestinationsNavigator
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_128),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(140.dp)
                    .height(140.dp)
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 16.dp
                    )
            )

            Text(
                text = stringResource(id = R.string.bmi_height),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp
                    )
            )

            TextField(
                value = height.inputText,
                onValueChange = {
                    if (it.length < 10) {
                        viewModel.onEvent(MainScreenEvent.EnterHeight(it))
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 8.dp
                    )
            )

            Text(
                text = stringResource(id = R.string.bmi_weight),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp
                    )
            )

            TextField(
                value = weight.inputText,
                onValueChange = {
                    if (it.length < 10) {
                        viewModel.onEvent(MainScreenEvent.EnterWeight(it))
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 8.dp
                    )
            )

            Button(
                onClick = {
                    viewModel.onEvent(MainScreenEvent.Report)
                    if (DataValidator.validate(
                            height = height.inputText,
                            weight = weight.inputText
                        )
                    ) {
                        navigator.navigate(
                            ReportScreenDestination(
                                height = height.inputText,
                                weight = weight.inputText
                            )
                        )
                    }
                },
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp
                )
            ) {
                Text(text = stringResource(id = R.string.bmi_btn))
            }

            Button(
                onClick = {
                    viewModel.onEvent(MainScreenEvent.ShowDialog)
                },
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp
                )
            ) {
                Text(text = stringResource(id = R.string.about_button))
            }
        }
    }
}
