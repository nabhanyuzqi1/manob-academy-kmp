package com.mnb.manobacademy.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.mnb.manobacademy.core.data.repository.AuthRepository
import com.mnb.manobacademy.core.data.repository.SupabaseAuthRepository
import com.mnb.manobacademy.core.utils.isDevelopmentMode
import com.mnb.manobacademy.models.BookingItem
import com.mnb.manobacademy.models.BottomNavItem // Import BottomNavItem
import com.mnb.manobacademy.views.auth.component.DefaultForgotPasswordComponent
import com.mnb.manobacademy.views.auth.component.DefaultLoginComponent
import com.mnb.manobacademy.views.auth.component.DefaultVerificationCodeComponent
import com.mnb.manobacademy.views.auth.component.ForgotPasswordComponent
import com.mnb.manobacademy.views.auth.component.LoginComponent
import com.mnb.manobacademy.views.auth.component.VerificationCodeComponent
import com.mnb.manobacademy.views.booking.component.BookingComponent // Import BookingComponent
import com.mnb.manobacademy.views.booking.component.DefaultBookingComponent // Import DefaultBookingComponent
import com.mnb.manobacademy.views.checkout.component.CheckoutComponent // Import CheckoutComponent
import com.mnb.manobacademy.views.checkout.component.DefaultCheckoutComponent // Import DefaultCheckoutComponent
import com.mnb.manobacademy.views.home.component.DefaultHomeComponent
import com.mnb.manobacademy.views.home.component.HomeComponent
import com.mnb.manobacademy.views.payment.component.DefaultPaymentComponent // Import DefaultPaymentComponent
import com.mnb.manobacademy.views.payment.component.PaymentComponent // Import PaymentComponent
import kotlinx.serialization.builtins.serializer

// Interface Root Component
interface RootComponent {
    val stack: Value<ChildStack<*, Child>>
    val navigation: StackNavigation<ScreenConfig>

    sealed interface Child {
        data object Splash : Child
        data class Login(val component: LoginComponent) : Child
        data object Register : Child
        data class VerificationCode(val component: VerificationCodeComponent) : Child
        data class ForgotPassword(val component: ForgotPasswordComponent) : Child
        data object Guide : Child
        data class Home(val component: HomeComponent) : Child
        data class Booking(val component: BookingComponent) : Child // Tambahkan Booking
        data class Checkout(val component: CheckoutComponent) : Child // Sudah ada, pastikan benar
        data class Payment(val component: PaymentComponent) : Child   // Sudah ada, pastikan benar
    }
}

// Implementasi Default Root Component
class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val authRepository: AuthRepository = SupabaseAuthRepository()
    override val navigation = StackNavigation<ScreenConfig>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = ScreenConfig.serializer(), // Gunakan serializer jika ScreenConfig @Serializable
            initialConfiguration = determineInitialScreenBasedOnMode(),
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun determineInitialScreenBasedOnMode(): ScreenConfig {
        return if (isDevelopmentMode) {
            println("INFO: Development mode active, starting at Home.")
            ScreenConfig.Home
        } else {
            println("INFO: Production mode active, starting at Splash.")
            ScreenConfig.Splash
        }
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun createChild(config: ScreenConfig, context: ComponentContext): RootComponent.Child =
        when (config) {
            is ScreenConfig.Splash -> RootComponent.Child.Splash
            is ScreenConfig.Login -> RootComponent.Child.Login(
                DefaultLoginComponent(
                    componentContext = context,
                    authRepository = authRepository,
                    onNavigateToRegister = { navigation.push(ScreenConfig.Register) },
                    onNavigateToForgotPassword = { navigation.push(ScreenConfig.ForgotPassword) },
                    onLoginSuccess = { navigation.replaceAll(ScreenConfig.Home) }
                )
            )
            is ScreenConfig.Register -> RootComponent.Child.Register
            is ScreenConfig.VerificationCode -> RootComponent.Child.VerificationCode(
                DefaultVerificationCodeComponent(
                    componentContext = context,
                    email = config.email,
                    onVerified = { navigation.replaceAll(ScreenConfig.Home) },
                    onNavigateBack = { navigation.pop() }
                )
            )
            is ScreenConfig.ForgotPassword -> RootComponent.Child.ForgotPassword(
                DefaultForgotPasswordComponent(
                    componentContext = context,
                    onNavigateBack = { navigation.pop() },
                    onResetInitiated = { navigation.pop() }
                )
            )
            is ScreenConfig.Guide -> RootComponent.Child.Guide
            is ScreenConfig.Home -> RootComponent.Child.Home(
                DefaultHomeComponent(
                    componentContext = context,
                    onLogout = { navigation.replaceAll(ScreenConfig.Login) },
                    onNavigateToCourseDetail = { courseId ->
                        println("Navigasi ke Detail Kursus: $courseId")
                        // navigation.push(ScreenConfig.CourseDetail(courseId))
                    },
                    onNavigateToLogin = { navigation.replaceAll(ScreenConfig.Login) },
                    onNavigateToNotifications = { println("Navigasi ke Notifikasi") },
                    onNavigateToAllClasses = { println("Navigasi ke Semua Kelas") },
                    onNavigateToAllInstructors = { println("Navigasi ke Semua Instruktur") },
                    onNavigateToAllNews = { println("Navigasi ke Semua Berita") },
                    onNavigateToNewsDetail = { newsId -> println("Navigasi ke Detail Berita: $newsId") },
                    // Tambahkan navigasi untuk Bottom Nav dari Home
                    onBottomNavItemSelected = { route ->
                        when (route) {
                            BottomNavItem.Home.route -> navigation.replaceAll(ScreenConfig.Home)
                            BottomNavItem.BookingFlow.route -> navigation.replaceAll(ScreenConfig.Booking) // Arahkan ke BookingScreen
                            // Tambahkan case lain untuk bottom nav jika perlu
                            else -> println("Bottom nav to $route not handled from Home")
                        }
                    }
                )
            )
            // --- Implementasi untuk Alur Checkout ---
            is ScreenConfig.Booking -> RootComponent.Child.Booking(
                DefaultBookingComponent(
                    componentContext = context,
                    onNavigateBack = {
                        // Kembali ke Home atau layar sebelumnya tergantung dari mana Booking diakses
                        if (stack.value.items.size > 1 && stack.value.items[stack.value.items.size - 2].configuration is ScreenConfig.Home) {
                            navigation.pop()
                        } else {
                            navigation.replaceAll(ScreenConfig.Home)
                        }
                    },
                    onNavigateToPayment = { itemsToCheckout ->
                        navigation.push(ScreenConfig.Checkout(itemsToCheckout))
                    },
                    onNavigateToDifferentTab = { newRoute -> // Handle navigasi bottom bar dari BookingScreen
                        when (newRoute) {
                            BottomNavItem.Home.route -> navigation.replaceAll(ScreenConfig.Home)
                            BottomNavItem.BookingFlow.route -> { /* Sudah di booking flow, tidak perlu navigasi */ }
                            // Tambahkan case lain
                            else -> println("Bottom nav to $newRoute not handled from Booking")
                        }
                    }
                )
            )
            is ScreenConfig.Checkout -> RootComponent.Child.Checkout(
                DefaultCheckoutComponent(
                    componentContext = context,
                    itemsFromBooking = config.items,
                    onNavigateBack = { navigation.pop() },
                    onNavigateToPaymentScreen = { itemsToPay, total ->
                        navigation.push(ScreenConfig.Payment(itemsToPay, total))
                    }
                )
            )
            is ScreenConfig.Payment -> RootComponent.Child.Payment(
                DefaultPaymentComponent(
                    componentContext = context,
                    itemsFromCheckout = config.items,
                    totalAmountFromCheckout = config.totalAmount,
                    onNavigateBack = { navigation.pop() },
                    onPaymentSuccess = {
                        println("Pembayaran Berhasil! Kembali ke Home.")
                        navigation.replaceAll(ScreenConfig.Home) // Atau ke layar sukses pesanan
                    }
                )
            )
        }
}
