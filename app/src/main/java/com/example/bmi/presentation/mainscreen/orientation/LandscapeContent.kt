package com.example.bmi.presentation.mainscreen.orientation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bmi.R
import com.example.bmi.presentation.mainscreen.MainScreenEvent
import com.example.bmi.presentation.mainscreen.MainScreenState
import com.example.bmi.presentation.mainscreen.MainViewModel
import com.example.bmi.presentation.util.DataValidator
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun LandscapeContent(
    height: MainScreenState,
    weight: MainScreenState,
    viewModel: MainViewModel,
    navigator: DestinationsNavigator
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_128),
                contentDescription = null,
                modifier = Modifier
                    .width(140.dp)
                    .height(140.dp)
                    .padding(
                        start = 16.dp,
                        top = 32.dp
                    )
            )

            Column {
                Text(
                    text = stringResource(id = R.string.bmi_height),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            top = 32.dp
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
                            start = 8.dp,
                            end = 8.dp,
                            top = 8.dp
                        )
                )

                Text(
                    text = stringResource(id = R.string.bmi_weight),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
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
                            start = 8.dp,
                            end = 8.dp,
                            top = 8.dp
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
//                            navigator.navigate(
//                                ReportScreenDestination(
//                                    feet = ,
//                                    weight = weight.inputText
//                                )
//                            )
                        }
                    },
                    modifier = Modifier.padding(
                        start = 8.dp,
                        top = 8.dp
                    )
                ) {
                    Text(text = stringResource(id = R.string.bmi_btn))
                }

                Button(
                    onClick = {
                        viewModel.onEvent(MainScreenEvent.ShowDialog)
                    },
                    modifier = Modifier.padding(
                        start = 8.dp,
                        top = 8.dp
                    )
                ) {
                    Text(text = stringResource(id = R.string.about_button))
                }
            }
        }
    }
}
