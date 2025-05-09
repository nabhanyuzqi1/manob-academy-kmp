package com.mnb.manobacademy.navigation

// Import komponen dan navigasi Decompose
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi // Import jika OptIn diperlukan di createChild
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation // Gunakan StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop // Import pop
import com.arkivanov.decompose.router.stack.push // Import push
import com.arkivanov.decompose.router.stack.replaceAll // Import replaceAll
import com.arkivanov.decompose.value.Value
// Import komponen fitur
import com.mnb.manobacademy.core.data.repository.AuthRepository
import com.mnb.manobacademy.core.data.repository.SupabaseAuthRepository
import com.mnb.manobacademy.core.utils.isDevelopmentMode // Import flag development
import com.mnb.manobacademy.views.auth.component.DefaultForgotPasswordComponent
import com.mnb.manobacademy.views.auth.component.ForgotPasswordComponent
import com.mnb.manobacademy.views.auth.component.DefaultLoginComponent
import com.mnb.manobacademy.views.auth.component.LoginComponent
import com.mnb.manobacademy.views.auth.component.DefaultVerificationCodeComponent
import com.mnb.manobacademy.views.auth.component.VerificationCodeComponent
import com.mnb.manobacademy.views.home.component.DefaultHomeComponent
import com.mnb.manobacademy.views.home.component.HomeComponent
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
        data class Login(val component: LoginComponent) : Child
        data object Register : Child // Register mungkin juga perlu jadi data class nanti
        data class VerificationCode(val component: VerificationCodeComponent) : Child
        data class ForgotPassword(val component: ForgotPasswordComponent) : Child
        data object Guide : Child
        data class Home(val component: HomeComponent) : Child

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

            // --- GUNAKAN FLAG UNTUK MENENTUKAN LAYAR AWAL ---
            initialConfiguration = determineInitialScreenBasedOnMode(),
            // -------------------------------------------------

            handleBackButton = true,
            childFactory = ::createChild
        )

    /**
     * Menentukan konfigurasi layar awal berdasarkan mode aplikasi.
     */
    private fun determineInitialScreenBasedOnMode(): ScreenConfig {
        return if (isDevelopmentMode) {
            println("INFO: Development mode active, starting at Home.")
            ScreenConfig.Home // Langsung ke Home jika development
        } else {
            println("INFO: Production mode active, starting at Splash.")
            ScreenConfig.Splash // Mulai dari Splash jika production
        }
    }

    // Fungsi factory untuk membuat instance Child berdasarkan ScreenConfig
    // Tambahkan OptIn jika diperlukan oleh Default...Component constructors
    @OptIn(DelicateDecomposeApi::class)
    private fun createChild(config: ScreenConfig, context: ComponentContext): RootComponent.Child =
        when (config) {
            // Splash tetap perlu ditangani jika dinavigasi manual atau saat production
            is ScreenConfig.Splash -> Splash
            is ScreenConfig.Login -> Login(
                DefaultLoginComponent(
                    componentContext = context,
                    authRepository = authRepository,
                    onNavigateToRegister = { navigation.push(ScreenConfig.Register) },
                    onNavigateToForgotPassword = { navigation.push(ScreenConfig.ForgotPassword) },
                    onLoginSuccess = { navigation.replaceAll(ScreenConfig.Home) }
                )
            )

            is ScreenConfig.Register -> Register // Nanti perlu diubah jika Register butuh Component
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

            is ScreenConfig.Guide -> Guide // Kembalikan data object Guide
            is ScreenConfig.Home -> Home(
                // Parameter DefaultHomeComponent telah dilengkapi
                DefaultHomeComponent(
                    componentContext = context,
                    onLogout = {
                        println("Navigasi dari Home ke Login (Logout)")
                        navigation.replaceAll(ScreenConfig.Login)
                    },
                    onNavigateToCourseDetail = { courseId ->
                        println("Navigasi ke Detail Kursus: $courseId")
                        // TODO: Implementasi navigasi ke detail kursus. Contoh:
                        // navigation.push(ScreenConfig.CourseDetail(courseId))
                    },
                    onNavigateToLogin = { // Callback ini mungkin redundan jika sudah ada onLogout
                        println("Permintaan navigasi ke Login dari Home")
                        navigation.replaceAll(ScreenConfig.Login)
                    },
                    // Callback tambahan yang diperlukan oleh DefaultHomeComponent:
                    onNavigateToNotifications = {
                        println("Navigasi ke Notifikasi")
                        // TODO: Implementasi navigasi ke layar notifikasi. Contoh:
                        // navigation.push(ScreenConfig.Notifications)
                    },
                    onNavigateToAllClasses = {
                        println("Navigasi ke Semua Kelas")
                        // TODO: Implementasi navigasi ke layar semua kelas. Contoh:
                        // navigation.push(ScreenConfig.AllClasses)
                    },
                    onNavigateToAllInstructors = {
                        println("Navigasi ke Semua Instruktur")
                        // TODO: Implementasi navigasi ke layar semua instruktur. Contoh:
                        // navigation.push(ScreenConfig.AllInstructors)
                    },
                    onNavigateToAllNews = {
                        println("Navigasi ke Semua Berita")
                        // TODO: Implementasi navigasi ke layar semua berita. Contoh:
                        // navigation.push(ScreenConfig.AllNews)
                    },
                    onNavigateToNewsDetail = { newsId ->
                        println("Navigasi ke Detail Berita: $newsId")
                        // TODO: Implementasi navigasi ke detail berita. Contoh:
                        // navigation.push(ScreenConfig.NewsDetail(newsId))
                    }
                )
            )

        }
}
}
