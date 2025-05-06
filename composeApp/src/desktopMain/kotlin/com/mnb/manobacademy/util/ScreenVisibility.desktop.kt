// --- File: composeApp/src/desktopMain/kotlin/com/mnb/manobacademy/ScreenVisibility.kt ---
package com.mnb.manobacademy.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mnb.manobacademy.views.auth.ui.SplashScreen
import com.mnb.manobacademy.navigation.RootComponent

actual fun shouldShowSplash(root: RootComponent, onNavigateToLogin: () -> Unit): @Composable () -> Unit {
    return {
        SplashScreen(onNavigateToLogin = onNavigateToLogin)
    }
}

@Composable
internal actual fun getScreenHeightDp(): Dp {
    // Untuk Desktop, logika penyesuaian tinggi mobile tidak relevan.
    // Kembalikan nilai yang sangat besar agar kondisi > threshold selalu false,
    // atau 0.dp jika itu lebih masuk akal untuk logika Anda.
    return 10000.dp
}