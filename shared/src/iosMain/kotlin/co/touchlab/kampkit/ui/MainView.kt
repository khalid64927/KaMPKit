package co.touchlab.kampkit.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController
import co.touchlab.kampkit.app.App

fun MainViewController() = ComposeUIViewController { App() }