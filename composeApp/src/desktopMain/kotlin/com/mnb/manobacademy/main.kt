package com.mnb.manobacademy

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.mnb.manobacademy.App
import com.mnb.manobacademy.navigation.DefaultRootComponent
import com.mnb.manobacademy.util.logAndReportCrash

@OptIn(ExperimentalDecomposeApi::class)
fun main() = application {

    Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
        logAndReportCrash(throwable)
    }

    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)
    val rootComponent = DefaultRootComponent(rootComponentContext)
    val windowState = rememberWindowState(width = 1024.dp, height = 768.dp)

    LifecycleController(lifecycle, windowState)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Manob Academy",
        state = windowState
    ) {
        App(root = rootComponent)
    }
}
