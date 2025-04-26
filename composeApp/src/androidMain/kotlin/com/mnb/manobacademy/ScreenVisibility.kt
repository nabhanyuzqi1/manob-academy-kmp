// --- File: composeApp/src/androidMain/kotlin/com/mnb/manobacademy/ScreenVisibility.kt ---
package com.mnb.manobacademy

import androidx.compose.runtime.Composable
import com.mnb.manobacademy.navigation.RootComponent

actual fun shouldShowSplash(root: RootComponent, onNavigateToLogin: () -> Unit): @Composable () -> Unit {
    return {
        onNavigateToLogin()
    }
}