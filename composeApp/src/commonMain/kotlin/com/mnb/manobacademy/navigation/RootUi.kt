package com.mnb.manobacademy.navigation // <<< Package ditambahkan

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.mnb.manobacademy.features.auth.ui.ForgotPasswordScreen
import com.mnb.manobacademy.features.auth.ui.GuideScreen // <<< IMPORT GuideScreen >>>
import com.mnb.manobacademy.features.auth.ui.LoginScreen
import com.mnb.manobacademy.features.auth.ui.RegistrationScreen
import com.mnb.manobacademy.features.auth.ui.VerificationCodeScreen
import com.mnb.manobacademy.features.home.component.HomeComponent
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
        // 'when' harus mencakup semua tipe Child yang didefinisikan di RootComponent
        when (val child = it.instance) {
            is RootComponent.Child.Splash -> {
                // Jika Anda ingin splash hanya sekali, logika awal di RootComponent lebih baik
                // Jika splash tetap diperlukan sebelum Guide/Login:
                shouldShowSplash(
                    root = component,
                    // Navigasi ke layar setelah splash (ditentukan oleh initialConfiguration di RootComponent)
                    onNavigateToLogin = { /* Mungkin tidak perlu aksi di sini jika initial sudah benar */ }
                )()
            }
            is RootComponent.Child.Login -> LoginScreen(
                onNavigateToRegister = { component.navigation.push(ScreenConfig.Register) },
                onForgotPasswordClick = { component.navigation.push(ScreenConfig.ForgotPassword) },
                onLoginSuccess = { component.navigation.replaceAll(ScreenConfig.Home) }
            )
            is RootComponent.Child.Register -> RegistrationScreen(
                onNavigateToLogin = { component.navigation.pop() },
                onRegisterSuccess = {
                    val registeredEmail: String? = null // TODO: Dapatkan email
                    component.navigation.push(ScreenConfig.VerificationCode(email = registeredEmail))
                }
            )
            is RootComponent.Child.VerificationCode -> {
                val verificationComponent = child.component
                VerificationCodeScreen(
                    onNavigateBack = { component.navigation.pop() },
                    onVerifyClick = { code -> verificationComponent.onVerifyClicked(code) },
                    onResendClick = { verificationComponent.onResendClicked() },
                    emailAddress = verificationComponent.emailAddress
                )
            }
            is RootComponent.Child.ForgotPassword -> {
                val forgotPasswordComponent = child.component
                ForgotPasswordScreen(component = forgotPasswordComponent)
            }
            // <<< PENANGANAN UNTUK GUIDE >>>
            is RootComponent.Child.Guide -> GuideScreen(
                onGetStarted = {
                    // Setelah klik Mulai, navigasi ke Login (atau Register?)
                    // Tandai bahwa guide sudah dilewati (simpan di SharedPreferences/DataStore)
                    // TODO: Implement logic to mark guide as completed
                    component.navigation.replaceAll(ScreenConfig.Login) // Ganti semua stack dengan Login
                }
            )
            // -----------------------------
            is RootComponent.Child.Home -> {
                when(currentPlatform) {
                    PlatformType.ANDROID -> AndroidHomeScreen(child.component)
                    PlatformType.DESKTOP -> DesktopHomeScreen(child.component)
                    else -> {} // Handle platform lain jika ada
                }
            }
        }
    }
}

// --- Contoh Composable Platform Specific untuk Home (Placeholder) ---
@Composable
fun AndroidHomeScreen(component: HomeComponent) {
    HomeScreen(component) // Untuk saat ini panggil yang sama
}

@Composable
fun DesktopHomeScreen(component: HomeComponent) {
    HomeScreen(component) // Untuk saat ini panggil yang sama
}
