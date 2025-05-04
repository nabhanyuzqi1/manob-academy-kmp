package com.mnb.manobacademy // Sesuaikan package Anda

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mnb.manobacademy.navigation.RootComponent // Ganti dengan path RootComponent Anda
import com.mnb.manobacademy.navigation.RootContent // Ganti dengan path RootContent Anda
import com.mnb.manobacademy.ui.theme.AppTheme // Ganti dengan path AppTheme Anda
import com.mnb.manobacademy.ui.theme.ProvidePlatformSpecificDimens // <- Import fungsi expect

/**
 * Root Composable aplikasi (commonMain).
 * Menggunakan ProvidePlatformSpecificDimens (expect/actual) untuk menangani dimensi.
 *
 * @param root Komponen root dari navigasi/state management Anda.
 */
@Composable
fun App(root: RootComponent) {
    // Panggil fungsi expect yang akan diimplementasikan oleh platform
    ProvidePlatformSpecificDimens {
        // AppTheme dan RootContent sekarang berada di dalam provider dimensi
        // yang diatur oleh implementasi 'actual' platform.
        AppTheme() {
            RootContent(
                component = root,
                modifier = Modifier
                    .fillMaxSize()
                    // Padding ini mungkin lebih baik ditangani di layar individual
            )
        }
    }
}
