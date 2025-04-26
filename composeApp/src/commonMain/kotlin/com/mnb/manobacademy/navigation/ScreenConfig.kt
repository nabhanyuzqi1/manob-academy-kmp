// --- File: composeApp/src/commonMain/kotlin/navigation/ScreenConfig.kt ---
package com.mnb.manobacademy.navigation

// import com.arkivanov.essenty.parcelable.Parcelable // Kembalikan jika Essenty diperbaiki
// import com.arkivanov.essenty.parcelable.Parcelize

// Definisikan layar-layar yang bisa dinavigasi
// @Parcelize // Kembalikan jika Essenty diperbaiki
sealed interface ScreenConfig /* : Parcelable */ { // Kembalikan jika Essenty diperbaiki
    // @Parcelize // Kembalikan jika Essenty diperbaiki
    data object Splash : ScreenConfig
    // @Parcelize // Kembalikan jika Essenty diperbaiki
    data object Login : ScreenConfig
    // @Parcelize // Kembalikan jika Essenty diperbaiki
    data object Register : ScreenConfig
    // @Parcelize // Kembalikan jika Essenty diperbaiki
    data object Home : ScreenConfig
}
