package com.example.bmi.presentation.mainscreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bmi.R
import com.example.bmi.presentation.component.UiEvent
import com.example.bmi.presentation.destinations.ReportScreenDestination
import com.example.bmi.presentation.util.DataValidator
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@RootNavGraph(start = true)
@Destination
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val height = viewModel.heightState.value
    val weight = viewModel.weightState.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { value: UiEvent ->
            when (value) {
                is UiEvent.SnackBarEvent -> {
                    Toast.makeText(context, context.getText(value.text), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

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
                    .align(CenterHorizontally)
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
        }
    }
}
