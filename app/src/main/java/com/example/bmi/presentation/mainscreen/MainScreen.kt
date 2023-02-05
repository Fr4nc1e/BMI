package com.example.bmi.presentation.mainscreen

import android.content.Intent
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bmi.R
import com.example.bmi.presentation.component.UiEvent
import com.example.bmi.presentation.mainscreen.orientation.LandscapeContent
import com.example.bmi.presentation.mainscreen.orientation.PortraitContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@RootNavGraph(start = true)
@Destination
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onFinish: () -> Unit,
    navigator: DestinationsNavigator
) {
    val height = viewModel.heightState.value
    val weight = viewModel.weightState.value
    val showDialog = viewModel.showDialogState
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    var orientation by remember { mutableStateOf(ORIENTATION_PORTRAIT) }
    var expanded by remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(MainScreenEvent.ShowDialog) },
            title = { Text(text = stringResource(id = R.string.about_button)) },
            text = { Text(text = stringResource(id = R.string.about_msg)) },
            confirmButton = {
                Button(onClick = { viewModel.onEvent(MainScreenEvent.ShowDialog) }) {
                    Text(text = "OK")
                }
            }
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { value: UiEvent ->
            when (value) {
                is UiEvent.SnackBarEvent -> {
                    Toast.makeText(context, context.getText(value.text), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.app_name))
            },
            actions = {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            val uri = Uri.parse("http://en.wikipedia.org/wiki/Body_mass_index")
                            val intents = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intents)
                        }
                    ) {
                        Text(text = stringResource(id = R.string.bmi_wiki))
                    }
                    DropdownMenuItem(
                        onClick = {
                            onFinish()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.exit))
                    }
                }
            }
        )
        when (orientation) {
            ORIENTATION_LANDSCAPE -> {
                LandscapeContent(
                    height = height,
                    weight = weight,
                    viewModel = viewModel,
                    navigator = navigator
                )
            }
            ORIENTATION_PORTRAIT -> {
                PortraitContent(
                    height = height,
                    weight = weight,
                    viewModel = viewModel,
                    navigator = navigator
                )
            }
        }
    }
}
