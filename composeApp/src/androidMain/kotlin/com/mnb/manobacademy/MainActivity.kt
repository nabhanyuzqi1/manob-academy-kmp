package com.mnb.manobacademy // Sesuaikan package

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext // Import DefaultComponentContext
import com.arkivanov.decompose.defaultComponentContext // <<< Pastikan ini diimpor
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.LifecycleRegistry // Import LifecycleRegistry
// Import UI Screens
import com.mnb.manobacademy.features.auth.ui.ForgotPasswordScreen // Import ForgotPasswordScreen
import com.mnb.manobacademy.features.auth.ui.GuideScreen // <<< IMPORT GuideScreen >>>
import com.mnb.manobacademy.features.auth.ui.LoginScreen
import com.mnb.manobacademy.features.auth.ui.RegistrationScreen
import com.mnb.manobacademy.features.auth.ui.SplashScreen
import com.mnb.manobacademy.features.auth.ui.VerificationCodeScreen
// Import Komponen (untuk dummy preview)
import com.mnb.manobacademy.features.auth.component.ForgotPasswordComponent
import com.mnb.manobacademy.features.auth.component.ResetMethod
// Import Navigation & App
import com.mnb.manobacademy.navigation.DefaultRootComponent
// import com.mnb.manobacademy.navigation.RootContent // Tidak perlu diimport jika hanya memanggil App()
import com.mnb.manobacademy.App // Import App Composable
// Import Tema
import com.mnb.manobacademy.ui.theme.AppTheme // <- Import AppTheme Anda
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            // Tambahkan kode berikut untuk mengatur durasi splash screen
            setKeepOnScreenCondition { true }
            lifecycleScope.launch {
                delay(2000)
                setKeepOnScreenCondition { false }
            }
            super.onCreate(savedInstanceState)
        }
        // Gunakan defaultComponentContext() dari Decompose untuk membuat ComponentContext dasar
        // Pastikan constructor DefaultRootComponent menerima ComponentContext
        val root = DefaultRootComponent(defaultComponentContext()) // <<< Panggil defaultComponentContext()

        WindowCompat.setDecorFitsSystemWindows(window, false) // <<< Add this line

        setContent {
            App(root = root)
        }
    }
}

// --- Bagian Preview ---

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AppAndroidPreview() {
    AppTheme {
        LoginScreenPreview() // Tampilkan salah satu layar sebagai contoh
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(
            onNavigateToRegister = {},
            onLoginSuccess = {},
            onForgotPasswordClick = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    AppTheme {
        RegistrationScreen(
            onNavigateToLogin = {},
            onRegisterSuccess = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SplashScreenPreview() {
    AppTheme {
        SplashScreen (
            onNavigateToLogin = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun VerificationCodeScreenPreview() {
    AppTheme {
        VerificationCodeScreen (
            onNavigateBack = {},
            onVerifyClick = {},
            onResendClick = {},
            emailAddress = "preview@example.com"
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    AppTheme {
        val dummyComponent = object : ForgotPasswordComponent {
            override val state: Value<ForgotPasswordComponent.State> =
                MutableValue(ForgotPasswordComponent.State(
                    maskedPhoneNumber = "+62 812-xxxx-5678",
                    maskedEmail = "preview-xxx@example.com",
                    selectedMethod = ResetMethod.NONE,
                    isResetEnabled = false
                ))
            override fun onMethodSelected(method: ResetMethod) {}
            override fun onResetClicked() {}
            override fun onBackClicked() {}
        }
        ForgotPasswordScreen(component = dummyComponent)
    }
}

// --- Preview untuk GuideScreen ---
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GuideScreenPreview() { // <<< Nama fungsi preview baru
    AppTheme { // <<< Bungkus dengan AppTheme
        GuideScreen(
            onGetStarted = {} // <<< Berikan lambda kosong
        )
    }
}
// -------------------------------

