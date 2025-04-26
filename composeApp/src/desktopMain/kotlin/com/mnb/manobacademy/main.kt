package com.mnb.manobacademy // Sesuaikan dengan package Anda

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.mnb.manobacademy.navigation.DefaultRootComponent // Import Root Component Anda
// Import Napier jika digunakan
// import io.github.aakira.napier.DebugAntilog
// import io.github.aakira.napier.Napier

@OptIn(ExperimentalDecomposeApi::class) // Diperlukan untuk LifecycleController
fun main() {
    // Napier.base(DebugAntilog()) // Inisialisasi Napier jika digunakan

    // 1. Buat LifecycleRegistry untuk Decompose
    val lifecycle = LifecycleRegistry()

    // 2. Buat instance DefaultRootComponent, berikan ComponentContext baru
    val rootComponent = DefaultRootComponent(DefaultComponentContext(lifecycle))

    application {
        val windowState = rememberWindowState(width = 800.dp, height = 600.dp)

        // 3. Kontrol lifecycle Decompose agar sesuai dengan window (Penting!)
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            title = "Manob Academy (Decompose)", // Judul bisa disesuaikan
            state = windowState
            // onKeyEvent = { /* Handle back button jika perlu di desktop */ false }
        ) {
            // 4. Panggil App dan berikan instance rootComponent
            App(root = rootComponent) // <-- Berikan parameter root
        }
    }
}