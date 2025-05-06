package com.mnb.manobacademy.navigation

// Import komponen dan navigasi Decompose
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation // Gunakan StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop // Import pop
import com.arkivanov.decompose.router.stack.push // <<< Import push >>>
import com.arkivanov.decompose.router.stack.replaceAll // Import replaceAll
import com.arkivanov.decompose.value.Value
// Import komponen fitur
import com.mnb.manobacademy.core.data.repository.AuthRepository // <<< Import AuthRepository
import com.mnb.manobacademy.core.data.repository.SupabaseAuthRepository // <<< Import Implementasi (atau dari DI)
import com.mnb.manobacademy.features.auth.component.DefaultForgotPasswordComponent
import com.mnb.manobacademy.features.auth.component.ForgotPasswordComponent
import com.mnb.manobacademy.features.auth.component.DefaultVerificationCodeComponent
import com.mnb.manobacademy.features.auth.component.VerificationCodeComponent
import com.mnb.manobacademy.features.auth.component.DefaultLoginComponent
import com.mnb.manobacademy.features.auth.component.LoginComponent
import com.mnb.manobacademy.features.home.component.DefaultHomeComponent
import com.mnb.manobacademy.features.home.component.HomeComponent
// Import Child dari RootComponent agar lebih pendek saat digunakan
import com.mnb.manobacademy.navigation.RootComponent.Child.*


// Interface Root Component
interface RootComponent {
    val stack: Value<ChildStack<*, Child>>
    val navigation: StackNavigation<ScreenConfig>

    // Sealed interface untuk Child screens
    sealed interface Child {
        data object Splash : Child
        // <<< Ubah Login menjadi data class >>>
        data class Login(val component: LoginComponent) : Child
        data object Register : Child // Register mungkin juga perlu jadi data class nanti
        data class VerificationCode(val component: VerificationCodeComponent) : Child
        data class ForgotPassword(val component: ForgotPasswordComponent) : Child
        data object Guide : Child
        data class Home(val component: HomeComponent) : Child
    }
}

// Implementasi Default Root Component
class DefaultRootComponent(
    componentContext: ComponentContext // Terima ComponentContext dari luar
) : RootComponent, ComponentContext by componentContext { // Delegasikan ComponentContext

    // Buat instance AuthRepository (idealnya dari DI)
    private val authRepository: AuthRepository = SupabaseAuthRepository()

    override val navigation = StackNavigation<ScreenConfig>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = null, // Atau ScreenConfig.serializer() jika @Serializable
            initialConfiguration = ScreenConfig.Splash,
            handleBackButton = true,
            childFactory = ::createChild
        )

    // Fungsi factory untuk membuat instance Child berdasarkan ScreenConfig
    @OptIn(DelicateDecomposeApi::class)
    private fun createChild(config: ScreenConfig, context: ComponentContext): RootComponent.Child =
        when (config) {
            is ScreenConfig.Splash -> Splash
            // <<< Buat instance LoginComponent >>>
            is ScreenConfig.Login -> Login(
                DefaultLoginComponent(
                    componentContext = context,
                    authRepository = authRepository, // Teruskan repository
                    onNavigateToRegister = { navigation.push(ScreenConfig.Register) },
                    onNavigateToForgotPassword = { navigation.push(ScreenConfig.ForgotPassword) },
                    onLoginSuccess = { navigation.replaceAll(ScreenConfig.Home) }
                )
            )
            // ---------------------------------
            is ScreenConfig.Register -> Register // Nanti perlu diubah jika Register butuh Component
            is ScreenConfig.VerificationCode -> VerificationCode(
                DefaultVerificationCodeComponent(
                    componentContext = context,
                    email = config.email,
                    onVerified = {
                        println("OTP Verified! Navigating to Home.")
                        navigation.replaceAll(ScreenConfig.Home)
                    },
                    onNavigateBack = { navigation.pop() }
                )
            )
            is ScreenConfig.ForgotPassword -> ForgotPassword(
                DefaultForgotPasswordComponent(
                    componentContext = context,
                    onNavigateBack = { navigation.pop() },
                    onResetInitiated = { method ->
                        println("Reset initiated via $method, navigate next...")
                        navigation.pop()
                    }
                )
            )
            is ScreenConfig.Guide -> Guide
            is ScreenConfig.Home -> Home(
                DefaultHomeComponent(
                    componentContext = context,
                    onLogout = {
                        println("Navigating from Home to Login (Logout)")
                        navigation.replaceAll(ScreenConfig.Login)
                    }
                )
            )
        }
}
