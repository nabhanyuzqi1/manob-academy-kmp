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
import com.mnb.manobacademy.views.home.ui.HomeScreen
import com.mnb.manobacademy.views.auth.ui.ForgotPasswordScreen
import com.mnb.manobacademy.views.auth.ui.GuideScreen // <<< IMPORT GuideScreen >>>
import com.mnb.manobacademy.views.auth.ui.LoginScreen
import com.mnb.manobacademy.views.auth.ui.RegistrationScreen
import com.mnb.manobacademy.views.auth.ui.VerificationCodeScreen
import com.mnb.manobacademy.views.home.component.HomeComponent
// Import utilitas platform
import com.mnb.manobacademy.util.PlatformType
import com.mnb.manobacademy.util.currentPlatform
// Import fungsi expect
import com.mnb.manobacademy.util.shouldShowSplash

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
                // Panggil shouldShowSplash, lambda onNavigate akan menentukan tujuan berikutnya
                shouldShowSplash(
                    root = component,
                    // <<< PERBAIKAN: Gunakan nama parameter yang benar (kemungkinan onNavigateToLogin) >>>
                    onNavigateToLogin = {
                        // TODO: Implementasikan logika untuk memeriksa apakah ini peluncuran pertama
                        val isFirstLaunch = true // Ganti dengan logika sebenarnya

                        println("DEBUG: Splash finished. Checking first launch (isFirstLaunch = $isFirstLaunch)...") // <<< Tambahkan log

                        if (isFirstLaunch) {
                            // Jika pertama kali, ganti stack dengan Guide
                            println("DEBUG: Navigating to Guide...") // <<< Tambahkan log
                            component.navigation.replaceAll(ScreenConfig.Guide)
                        } else {
                            // Jika bukan pertama kali, ganti stack dengan Login
                            println("DEBUG: Navigating to Login...") // <<< Tambahkan log
                            component.navigation.replaceAll(ScreenConfig.Login)
                        }
                    }
                )() // Panggil lambda Composable yang dikembalikan oleh shouldShowSplash
            }
            is RootComponent.Child.Login -> LoginScreen(
                component = child.component // <<< Ini sudah benar
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
            is RootComponent.Child.Guide -> GuideScreen(
                onGetStarted = {
                    // Setelah klik Mulai di Guide, navigasi ke Login
                    // TODO: Tandai bahwa guide sudah dilewati
                    println("DEBUG: Guide finished. Navigating to Login...") // <<< Tambahkan log
                    component.navigation.replaceAll(ScreenConfig.Login)
                }
            )
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
