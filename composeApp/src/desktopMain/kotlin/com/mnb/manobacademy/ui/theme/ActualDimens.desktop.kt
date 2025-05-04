package com.mnb.manobacademy.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo // Cara alternatif mendapatkan ukuran window
import androidx.compose.ui.unit.dp
// Atau jika Anda ingin akses langsung ke WindowState dari main.kt,
// Anda perlu meneruskannya atau menggunakan cara lain untuk berbagi state ukuran.
// Untuk kesederhanaan, kita gunakan LocalWindowInfo di sini.

/**
 * Implementasi aktual (actual) dari ProvidePlatformSpecificDimens untuk platform Desktop.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun ProvidePlatformSpecificDimens(content: @Composable () -> Unit) {
    // Dapatkan informasi window, termasuk ukuran container
    val windowInfo = LocalWindowInfo.current
    val windowWidth = windowInfo.containerSize.width.dp // Perlu konversi ke Dp jika belum

    // Tentukan set Dimensi berdasarkan lebar jendela
    // (Breakpoint bisa disesuaikan untuk desktop)
    val dimensions = when {
        windowWidth < 600.dp -> CompactDimens
        windowWidth < 840.dp -> MediumDimens
        else -> ExpandedDimens
    }

    // Sediakan Dimensi yang dipilih menggunakan CompositionLocalProvider
    CompositionLocalProvider(LocalDimens provides dimensions) {
        content() // Tampilkan konten yang sebenarnya
    }
}