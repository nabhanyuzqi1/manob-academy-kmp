package com.mnb.manobacademy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.mnb.manobacademy.views.auth.ui.ForgotPasswordScreen
import com.mnb.manobacademy.views.auth.ui.GuideScreen
import com.mnb.manobacademy.views.auth.ui.LoginScreen
import com.mnb.manobacademy.views.auth.ui.RegistrationScreen
import com.mnb.manobacademy.views.auth.ui.SplashScreen // Pastikan SplashScreen diimpor
import com.mnb.manobacademy.views.auth.ui.VerificationCodeScreen
import com.mnb.manobacademy.views.booking.ui.BookingScreen
import com.mnb.manobacademy.views.checkout.ui.CheckoutScreen
import com.mnb.manobacademy.views.home.component.HomeComponent
import com.mnb.manobacademy.views.home.ui.HomeScreen
import com.mnb.manobacademy.views.payment.ui.PaymentScreen

@OptIn(DelicateDecomposeApi::class)
@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            // PERBAIKAN: Memanggil SplashScreen secara langsung dan konsisten
            is RootComponent.Child.Splash -> {
                SplashScreen(
                    onNavigateToLogin = {
                        // TODO: Ganti dengan logika pengecekan pengguna pertama kali yang sebenarnya
                        val isFirstLaunch = true
                        if (isFirstLaunch) {
                            component.navigation.replaceAll(ScreenConfig.Guide)
                        } else {
                            component.navigation.replaceAll(ScreenConfig.Login)
                        }
                    }
                )
            }

            is RootComponent.Child.Login -> {
                LoginScreen(component = child.component)
            }

            // PERBAIKAN UTAMA: Menghapus cast yang salah untuk menghilangkan warning
            is RootComponent.Child.Register -> {
                RegistrationScreen(
                    onNavigateToLogin = { component.navigation.pop() },
                    // Cukup teruskan lambda, tipe data akan diinferensi secara otomatis
                    onRegisterSuccess = { email ->
                        component.navigation.push(ScreenConfig.VerificationCode(email = email))
                    }
                )
            }

            // PERBAIKAN: Panggilan disederhanakan, hanya menggunakan component
            is RootComponent.Child.VerificationCode -> {
                // --- PERBAIKAN DI SINI ---
                // Panggilan menjadi sangat bersih, hanya meneruskan component.
                // Tidak akan ada lagi error 'No value passed for parameter'.
                VerificationCodeScreen(component = child.component)
            }

            is RootComponent.Child.ForgotPassword -> {
                ForgotPasswordScreen(component = child.component)
            }

            is RootComponent.Child.Guide -> {
                GuideScreen(
                    onGetStarted = {
                        component.navigation.replaceAll(ScreenConfig.Login)
                    }
                )
            }

            // PERBAIKAN: Disederhanakan, tidak perlu fungsi wrapper
            is RootComponent.Child.Home -> {
                HomeScreen(component = child.component)
            }

            is RootComponent.Child.Booking -> {
                BookingScreen(component = child.component)
            }
            is RootComponent.Child.Checkout -> {
                CheckoutScreen(component = child.component)
            }
            is RootComponent.Child.Payment -> {
                PaymentScreen(component = child.component)
            }
        }
    }
}
