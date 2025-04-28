// --- File: composeApp/src/desktopMain/kotlin/com/mnb/manobacademy/main.kt ---
// *** PERBAIKAN DI SINI ***
package com.mnb.manobacademy // Sesuaikan dengan package Anda

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.mnb.manobacademy.navigation.DefaultRootComponent

// Import Napier jika digunakan
// import io.github.aakira.napier.DebugAntilog
// import io.github.aakira.napier.Napier
// Hapus import SwingUtilities jika tidak digunakan lagi
// import javax.swing.SwingUtilities
// Import Dispatchers jika diperlukan (misal untuk scope)
// import kotlinx.coroutines.Dispatchers
// import kotlinx.coroutines.swing.Swing

@OptIn(ExperimentalDecomposeApi::class) // Diperlukan untuk LifecycleController
fun main() {
    // Napier.base(DebugAntilog()) // Inisialisasi Napier jika digunakan

    // 1. Buat LifecycleRegistry untuk Decompose
    val lifecycle = LifecycleRegistry()

    // 2. Buat instance DefaultComponentContext di Main Thread
    // Cara yang lebih disarankan adalah membuat context sebelum application block
    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)

    // 3. Buat RootComponent
    val rootComponent = DefaultRootComponent(rootComponentContext)

    application {
        val windowState = rememberWindowState(width = 800.dp, height = 600.dp)

        // 4. Kontrol lifecycle Decompose agar sesuai dengan window (Penting!)
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            title = "Manob Academy (Decompose)", // Judul bisa disesuaikan
            state = windowState
            // onKeyEvent = { /* Handle back button jika perlu di desktop */ false }
        ) {
            // 5. Sediakan CompositionLocalProvider jika diperlukan, tapi hapus LocalUriHandler
            // CompositionLocalProvider(
            // HAPUS ATAU GANTI BARIS INI:
            // androidx.compose.ui.platform.LocalUriHandler provides androidx.compose.ui.platform.DesktopUriHandler(),
            // Anda mungkin perlu menyediakan dispatcher lain jika diperlukan
            // ) {
            // 6. Panggil App dan berikan instance rootComponent
            App(root = rootComponent) // <-- Berikan parameter root
            // }
        }
    }
}