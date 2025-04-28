package com.mnb.manobacademy.ui.theme // Sesuaikan package

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
internal actual fun platformSpecificColorScheme(darkTheme: Boolean, dynamicColor: Boolean): ColorScheme? {
    // Dynamic color tidak didukung di Desktop secara default
    return null
}

@Composable
internal actual fun PlatformSpecificThemeEffects(darkTheme: Boolean, colorScheme: ColorScheme) {
    // Tidak ada efek status bar standar di Desktop.
    // Bisa ditambahkan kustomisasi window di sini jika perlu.
}