// --- File: composeApp/src/desktopMain/kotlin/com/mnb/manobacademy/ScreenVisibility.kt ---
package com.mnb.manobacademy

import androidx.compose.runtime.Composable
import com.mnb.manobacademy.features.auth.ui.SplashScreen
import com.mnb.manobacademy.navigation.RootComponent

actual fun shouldShowSplash(root: RootComponent, onNavigateToLogin: () -> Unit): @Composable () -> Unit {
    return {
        SplashScreen(onNavigateToLogin = onNavigateToLogin)
    }
}