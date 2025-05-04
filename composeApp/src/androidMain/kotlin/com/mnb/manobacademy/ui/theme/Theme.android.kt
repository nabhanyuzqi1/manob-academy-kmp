@file:Suppress("DEPRECATION")

package com.mnb.manobacademy.ui.theme

import android.R
import android.app.Activity
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
internal actual fun platformSpecificColorScheme(darkTheme: Boolean, dynamicColor: Boolean): ColorScheme? {
    val context = LocalContext.current
    return if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        null
    }
}

@Composable
internal actual fun PlatformSpecificThemeEffects(darkTheme: Boolean, colorScheme: ColorScheme) {

}