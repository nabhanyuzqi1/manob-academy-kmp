// --- File: composeApp/src/commonMain/kotlin/navigation/RootUi.kt ---
package com.mnb.manobacademy.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.mnb.manobacademy.features.auth.ui.LoginScreen
import com.mnb.manobacademy.features.auth.ui.RegistrationScreen
// import com.mnb.manobacademy.features.auth.ui.SplashScreen // Tidak dipanggil langsung lagi
import com.mnb.manobacademy.features.home.ui.HomeScreen
// Import utilitas platform
import com.mnb.manobacademy.util.PlatformType
import com.mnb.manobacademy.util.currentPlatform
// Import fungsi expect
import com.mnb.manobacademy.shouldShowSplash

@OptIn(DelicateDecomposeApi::class)
@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.stack, // Gunakan stack dari RootComponent
        modifier = modifier,
        animation = stackAnimation(fade()) // Animasi transisi dasar
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Splash -> {
                // Panggil fungsi expect untuk mendapatkan Composable Splash atau logika
                shouldShowSplash(
                    root = component,
                    onNavigateToLogin = { component.navigation.replaceAll(ScreenConfig.Login) }
                )() // <-- Panggil lambda Composable yang dikembalikan
            }
            is RootComponent.Child.Login -> LoginScreen( // LoginScreen sekarang sudah platform-aware
                onNavigateToRegister = { component.navigation.push(ScreenConfig.Register) },
                onLoginSuccess = { component.navigation.replaceAll(ScreenConfig.Home) }
            )
            is RootComponent.Child.Register -> RegistrationScreen( // RegistrationScreen sekarang sudah platform-aware
                onNavigateToLogin = { component.navigation.pop() },
                onRegisterSuccess = { component.navigation.replaceAll(ScreenConfig.Login) }
            )
            is RootComponent.Child.Home -> {
                // Contoh: HomeScreen juga bisa dibuat platform-aware
                when(currentPlatform) {
                    PlatformType.ANDROID -> HomeScreen(child.component)
                    PlatformType.DESKTOP -> HomeScreen(child.component)
                    else -> {}
                }
            }
        }
    }
}