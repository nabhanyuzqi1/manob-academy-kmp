package com.mnb.manobacademy.navigation

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mnb.manobacademy.models.BookingItem

/**
 * Sealed interface yang mendefinisikan semua kemungkinan konfigurasi layar
 * yang dapat dinavigasi dalam aplikasi.
 */
@Parcelize // Jika semua config adalah Parcelable, ini bisa diletakkan di sini
sealed interface ScreenConfig : Parcelable { // Implement Parcelable
    @Parcelize
    data object Splash : ScreenConfig
    @Parcelize
    data object Login : ScreenConfig
    @Parcelize
    data object Register : ScreenConfig
    @Parcelize
    data object ForgotPassword : ScreenConfig
    @Parcelize
    data class VerificationCode(val email: String?) : ScreenConfig
    @Parcelize
    data object Home : ScreenConfig
    @Parcelize
    data object Guide : ScreenConfig

    // --- Konfigurasi untuk Alur Checkout ---
    @Parcelize
    data object Booking : ScreenConfig // Layar awal pemilihan item booking

    @Parcelize
    data class Checkout(val items: List<BookingItem>) : ScreenConfig // Layar ringkasan checkout

    @Parcelize
    data class Payment(val items: List<BookingItem>, val totalAmount: Double) : ScreenConfig // Layar pemilihan metode pembayaran
}
