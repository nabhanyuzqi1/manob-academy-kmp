package com.mnb.manobacademy // Sesuaikan package

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.arkivanov.decompose.defaultComponentContext
import com.mnb.manobacademy.features.auth.ui.RegistrationScreen
import com.mnb.manobacademy.features.auth.ui.SplashScreen
import com.mnb.manobacademy.navigation.DefaultRootComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Import Napier jika digunakan
// import io.github.aakira.napier.DebugAntilog
// import io.github.aakira.napier.Napier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Napier.base(DebugAntilog()) // Inisialisasi Napier jika digunakan
        var isChecking = true
        lifecycleScope.launch {
                delay(3000L)
                isChecking = false
            }
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                isChecking
            }

        }

        // 1. Buat DefaultRootComponent menggunakan defaultComponentContext()
        val root = DefaultRootComponent(defaultComponentContext())

        setContent {
            // 2. Berikan instance 'root' ke App()
            App(root = root)
        }
    }
}

// --- Bagian Preview (Lihat poin 2 di bawah) ---
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AppAndroidPreview() {
    // App() // <-- Memanggil App() di sini bermasalah
    // Sebaiknya preview komponen layar individual, lihat penjelasan di bawah
    // Contoh preview LoginScreen:
    LoginScreenPreview()
}

// Contoh Preview untuk LoginScreen (lebih disarankan)
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    // Import LoginScreen jika belum
    com.mnb.manobacademy.features.auth.ui.LoginScreen(
        onNavigateToRegister = {}, // Berikan lambda kosong untuk preview
        onLoginSuccess = {} // Berikan lambda kosong untuk preview
    )
}

// Anda bisa membuat preview serupa untuk SplashScreen dan RegistrationScreen
@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen(
        onNavigateToLogin = {},
        onRegisterSuccess = {}
    )
}

// Preview SplashScreen mungkin perlu sedikit penyesuaian jika ada LaunchedEffect
// Anda bisa membuat Composable wrapper untuk preview jika perlu
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen (
        onNavigateToLogin = {}
    )
    // Catatan: LaunchedEffect dengan delay mungkin tidak berjalan ideal di preview statis
}