package com.mnb.manobacademy // Sesuaikan dengan package Anda

// Hapus import yang tidak perlu terkait dimensi:
// import androidx.compose.runtime.CompositionLocalProvider
// import com.mnb.manobacademy.ui.theme.CompactDimens
// import com.mnb.manobacademy.ui.theme.ExpandedDimens
// import com.mnb.manobacademy.ui.theme.LocalDimens
// import com.mnb.manobacademy.ui.theme.MediumDimens
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.mnb.manobacademy.App // <- Import App dari commonMain
import com.mnb.manobacademy.navigation.RootComponent.DefaultRootComponent

@OptIn(ExperimentalDecomposeApi::class) // Diperlukan untuk LifecycleController
fun main() = application { // Pindahkan semua logika ke dalam application block
    // --- Setup Decompose ---
    // 1. Buat LifecycleRegistry (Aman di sini)
    val lifecycle = LifecycleRegistry()

    // 2. Buat DefaultComponentContext (Aman di sini, terkait dengan lifecycle)
    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)

    // 3. Buat RootComponent (Aman di sini, sebelum UI utama)
    val rootComponent = DefaultRootComponent(rootComponentContext)

    // --- Setup Window & UI ---
    // State untuk ukuran jendela Desktop
    val windowState = rememberWindowState(width = 1024.dp, height = 768.dp)

    // 4. Kontrol lifecycle Decompose agar sesuai dengan window (Penting!)
    LifecycleController(lifecycle, windowState)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Manob Academy", // Judul bisa disesuaikan
        state = windowState
    ) {
        // --- Tampilkan App ---
        // Panggil App Composable dari commonMain.
        // App akan memanggil ProvidePlatformSpecificDimens (expect/actual)
        // yang implementasi desktop-nya akan menangani dimensi.
        App(root = rootComponent)

        // **** CATATAN PENTING untuk Error NoSuchMethodError ****
        // Error java.lang.NoSuchMethodError: 'float androidx.compose.ui.util.MathHelpersKt.fastCbrt(float)'
        // biasanya disebabkan oleh KETIDAKCOCOKAN VERSI DEPENDENSI.
        // Pastikan semua dependensi androidx.compose.* (ui, material, animation, foundation, dll.)
        // menggunakan VERSI YANG SAMA di file build.gradle.kts Anda.
        // Periksa juga kompatibilitas versi Compose dengan:
        // - Versi Kotlin yang Anda gunakan
        // - Versi Compose Compiler Extension
        // - Versi Skiko (jika diatur secara eksplisit)
    }
}
