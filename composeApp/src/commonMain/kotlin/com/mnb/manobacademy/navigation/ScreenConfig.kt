package com.mnb.manobacademy.navigation

// Import komponen tidak diperlukan di sini, ScreenConfig hanya data/konfigurasi
// import com.mnb.manobacademy.features.auth.component.ForgotPasswordComponent
// import com.mnb.manobacademy.features.auth.component.VerificationCodeComponent
// import com.mnb.manobacademy.features.home.component.HomeComponent

// Import ini juga tidak diperlukan di sini
// import com.mnb.manobacademy.navigation.RootComponent.Child

// Jika Anda ingin menggunakan @Serializable nanti, uncomment import dan anotasi
// import kotlinx.serialization.Serializable

/**
 * Sealed interface yang mendefinisikan semua kemungkinan konfigurasi layar
 * yang dapat dinavigasi dalam aplikasi.
 * Setiap implementasi mewakili satu layar atau tujuan navigasi.
 */
// @Serializable // Uncomment jika ingin menggunakan kotlinx-serialization
sealed interface ScreenConfig {
    // Gunakan data object untuk layar tanpa parameter
    // @Serializable // Uncomment jika ingin menggunakan kotlinx-serialization
    data object Splash : ScreenConfig
    // @Serializable // Uncomment jika ingin menggunakan kotlinx-serialization
    data object Login : ScreenConfig
    // @Serializable // Uncomment jika ingin menggunakan kotlinx-serialization
    data object Register : ScreenConfig
    // @Serializable // Uncomment jika ingin menggunakan kotlinx-serialization
    data object ForgotPassword : ScreenConfig // Definisikan di sini

    // Gunakan data class untuk layar yang memerlukan parameter
    // @Serializable // Uncomment jika ingin menggunakan kotlinx-serialization
    data class VerificationCode(val email: String?) : ScreenConfig // Bawa email di sini

    // @Serializable // Uncomment jika ingin menggunakan kotlinx-serialization
    // Home bisa object atau class jika perlu parameter
    data object Home : ScreenConfig

    data object Guide : ScreenConfig // <-- Tambahkan ini

}
