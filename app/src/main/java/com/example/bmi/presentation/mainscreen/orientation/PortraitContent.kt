package com.example.bmi.presentation.mainscreen.orientation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bmi.R
import com.example.bmi.presentation.component.MyData
import com.example.bmi.presentation.component.SpinnerSample
import com.example.bmi.presentation.destinations.ReportScreenDestination
import com.example.bmi.presentation.mainscreen.MainScreenEvent
import com.example.bmi.presentation.mainscreen.MainScreenState
import com.example.bmi.presentation.mainscreen.MainViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalFoundationApi::class)
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
        val showContextMenu = remember {
            mutableStateOf(false)
        }
        val context = LocalContext.current
        val feetData = ArrayList<MyData>()
        val inchData = ArrayList<MyData>()
        context.resources.getStringArray(R.array.feet).forEachIndexed { index, s ->
            feetData.add(MyData(index, s))
        }
        context.resources.getStringArray(R.array.inches).forEachIndexed { index, s ->
            inchData.add(MyData(index, s))
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_128),
                    contentDescription = null,
                    modifier = Modifier
                        .width(140.dp)
                        .height(140.dp)
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            top = 16.dp
                        )
                        .combinedClickable(
                            onClick = {},
                            onLongClick = {
                                showContextMenu.value = true
                            }
                        )
                )
                DropdownMenu(
                    expanded = showContextMenu.value,
                    onDismissRequest = { showContextMenu.value = false }
                ) {
                    DropdownMenuItem(onClick = { viewModel.onEvent(MainScreenEvent.ShowDialog) }) {
                        Text(
                            text = stringResource(id = R.string.about_button),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    DropdownMenuItem(
                        onClick = {
                            val uri =
                                Uri.parse("http://en.wikipedia.org/wiki/Body_mass_index")
                            val intents = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intents)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.bmi_wiki),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }

            Text(
                text = stringResource(id = R.string.bmi_height),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp
                    )
            )

            SpinnerSample(
                list = feetData,
                preselected = feetData.first(),
                onSelectionChanged = {
                    viewModel.onEvent(MainScreenEvent.EnterFeet((it.id + 1).toString()))
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
            SpinnerSample(
                list = inchData,
                preselected = inchData.first(),
                onSelectionChanged = {
                    viewModel.onEvent(MainScreenEvent.EnterInch(it.id.toString()))
                }
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
                    navigator.navigate(
                        ReportScreenDestination(
                            feet = viewModel.feetState.value,
                            inch = viewModel.inchState.value,
                            weight = weight.inputText
                        )
                    )
//                    if (DataValidator.validate(
//                            height = height.inputText,
//                            weight = weight.inputText
//                        )
//                    ) {
//
//                    }
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
