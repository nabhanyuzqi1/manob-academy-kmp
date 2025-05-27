@file:Suppress("UNCHECKED_CAST")

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
import com.mnb.manobacademy.util.PlatformType
import com.mnb.manobacademy.util.currentPlatform
import com.mnb.manobacademy.util.shouldShowSplash
import com.mnb.manobacademy.views.auth.ui.ForgotPasswordScreen
import com.mnb.manobacademy.views.auth.ui.GuideScreen
import com.mnb.manobacademy.views.auth.ui.LoginScreen
import com.mnb.manobacademy.views.auth.ui.RegistrationScreen
import com.mnb.manobacademy.views.auth.ui.VerificationCodeScreen
import com.mnb.manobacademy.views.booking.ui.BookingScreen // Import BookingScreen
import com.mnb.manobacademy.views.checkout.ui.CheckoutScreen // Import CheckoutScreen
import com.mnb.manobacademy.views.home.component.HomeComponent
import com.mnb.manobacademy.views.home.ui.HomeScreen
import com.mnb.manobacademy.views.payment.ui.PaymentScreen // Import PaymentScreen

@OptIn(DelicateDecomposeApi::class)
@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Splash -> {
                shouldShowSplash(
                    root = component,
                    onNavigateToLogin = {
                        val isFirstLaunch = true // Ganti dengan logika sebenarnya
                        if (isFirstLaunch) {
                            component.navigation.replaceAll(ScreenConfig.Guide)
                        } else {
                            component.navigation.replaceAll(ScreenConfig.Login)
                        }
                    }
                )()
            }
            is RootComponent.Child.Login -> LoginScreen(component = child.component)
            is RootComponent.Child.Register -> RegistrationScreen(
                onNavigateToLogin = { component.navigation.pop() },
                onRegisterSuccess = { email -> // email dari RegistrationScreen
                    component.navigation.push(ScreenConfig.VerificationCode(email = email))
                } as () -> Unit
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
                ForgotPasswordScreen(component = child.component)
            }
            is RootComponent.Child.Guide -> GuideScreen(
                onGetStarted = {
                    component.navigation.replaceAll(ScreenConfig.Login)
                }
            )
            is RootComponent.Child.Home -> {
                when (currentPlatform) {
                    PlatformType.ANDROID -> AndroidHomeScreen(child.component)
                    PlatformType.DESKTOP -> DesktopHomeScreen(child.component)
                    else -> {}
                }
            }
            // --- Render UI untuk Alur Checkout ---
            is RootComponent.Child.Booking -> BookingScreen(component = child.component)
            is RootComponent.Child.Checkout -> CheckoutScreen(component = child.component)
            is RootComponent.Child.Payment -> PaymentScreen(component = child.component)
        }
    }
}

@Composable
fun AndroidHomeScreen(component: HomeComponent) {
    HomeScreen(component)
}

@Composable
fun DesktopHomeScreen(component: HomeComponent) {
    HomeScreen(component)
}
