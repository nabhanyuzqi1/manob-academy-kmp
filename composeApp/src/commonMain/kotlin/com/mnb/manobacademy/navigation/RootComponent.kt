package com.mnb.manobacademy.navigation

// Import komponen dan navigasi Decompose
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation // Gunakan StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop // Import pop
import com.arkivanov.decompose.router.stack.replaceAll // Import replaceAll
import com.arkivanov.decompose.value.Value
// Import komponen fitur
import com.mnb.manobacademy.features.auth.component.DefaultForgotPasswordComponent
import com.mnb.manobacademy.features.auth.component.ForgotPasswordComponent
import com.mnb.manobacademy.features.auth.component.DefaultVerificationCodeComponent
import com.mnb.manobacademy.features.auth.component.VerificationCodeComponent
import com.mnb.manobacademy.features.home.component.DefaultHomeComponent
import com.mnb.manobacademy.features.home.component.HomeComponent
// Import Child dari RootComponent agar lebih pendek saat digunakan
import com.mnb.manobacademy.navigation.RootComponent.Child.*


// Interface Root Component
interface RootComponent {
    val stack: Value<ChildStack<*, Child>>
    // Gunakan StackNavigation agar bisa pop/push/replaceAll
    val navigation: StackNavigation<ScreenConfig>

    // Sealed interface untuk Child screens
    sealed interface Child {
        data object Splash : Child
        data object Login : Child
        data object Register : Child
        data class VerificationCode(val component: VerificationCodeComponent) : Child
        data class ForgotPassword(val component: ForgotPasswordComponent) : Child
        data object Guide : Child // <<< TAMBAHKAN GUIDE DI SINI >>>
        data class Home(val component: HomeComponent) : Child
    }
}

// Implementasi Default Root Component
class DefaultRootComponent(
    componentContext: ComponentContext // Terima ComponentContext dari luar
) : RootComponent, ComponentContext by componentContext { // Delegasikan ComponentContext

    // Gunakan StackNavigation untuk kontrol navigasi
    override val navigation = StackNavigation<ScreenConfig>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = null, // Atau ScreenConfig.serializer() jika @Serializable
            // Tentukan initialConfiguration berdasarkan logika (misal: cek first launch)
            initialConfiguration = determineInitialScreen(), // Ganti ScreenConfig.Splash
            handleBackButton = true,
            childFactory = ::createChild
        )

    // Fungsi untuk menentukan layar awal (contoh sederhana)
    private fun determineInitialScreen(): ScreenConfig {
        // TODO: Implementasikan logika untuk memeriksa apakah ini peluncuran pertama
        val isFirstLaunch = true // Ganti dengan logika sebenarnya (misal: cek SharedPreferences)
        return if (isFirstLaunch) ScreenConfig.Guide else ScreenConfig.Login // Atau Splash jika masih perlu
    }


    // Fungsi factory untuk membuat instance Child berdasarkan ScreenConfig
    private fun createChild(config: ScreenConfig, context: ComponentContext): RootComponent.Child =
        when (config) {
            is ScreenConfig.Splash -> Splash
            is ScreenConfig.Login -> Login
            is ScreenConfig.Register -> Register
            is ScreenConfig.VerificationCode -> VerificationCode(
                DefaultVerificationCodeComponent(
                    componentContext = context,
                    email = config.email, // Pastikan ScreenConfig.VerificationCode punya 'email'
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
                        navigation.pop() // Atau navigasi ke layar konfirmasi
                    }
                )
            )
            // <<< PENANGANAN UNTUK GUIDE >>>
            is ScreenConfig.Guide -> Guide // Kembalikan data object Guide
            // -----------------------------
            is ScreenConfig.Home -> Home(
                DefaultHomeComponent(context)
            )
        }
}
