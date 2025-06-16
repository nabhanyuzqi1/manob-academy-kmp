package com.mnb.manobacademy.navigation

import com.mnb.manobacademy.models.BookingItem
import kotlinx.serialization.Serializable

/**
 * Sealed interface yang mendefinisikan semua kemungkinan konfigurasi layar
 * yang dapat dinavigasi dalam aplikasi.
 */
@Serializable // Jika semua config adalah Serializable, ini bisa diletakkan di sini
sealed interface ScreenConfig { // Implement Serializable
    @Serializable
    data object Splash : ScreenConfig
    @Serializable
    data object Login : ScreenConfig
    @Serializable
    data object Register : ScreenConfig
    @Serializable
    data object ForgotPassword : ScreenConfig
    @Serializable
    data class VerificationCode(val email: String?) : ScreenConfig
    @Serializable
    data object Home : ScreenConfig
    @Serializable
    data object Guide : ScreenConfig

    // --- Konfigurasi untuk Alur Checkout ---
    @Serializable
    data object Booking : ScreenConfig // Layar awal pemilihan item booking

    @Serializable
    data class Checkout(val items: List<BookingItem>) : ScreenConfig // Layar ringkasan checkout

    @Serializable
    data class Payment(val items: List<BookingItem>, val totalAmount: Double) : ScreenConfig // Layar pemilihan metode pembayaran
}
