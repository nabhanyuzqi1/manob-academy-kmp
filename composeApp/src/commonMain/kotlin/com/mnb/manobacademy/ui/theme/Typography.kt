package com.mnb.manobacademy.ui.theme // Sesuaikan package

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import manobacademykmp.composeapp.generated.resources.Res
import manobacademykmp.composeapp.generated.resources.roboto_bold
import manobacademykmp.composeapp.generated.resources.roboto_regular
import manobacademykmp.composeapp.generated.resources.roboto_semibold

// --- Memuat Font dari Resources ---
// Pastikan path resource ("font/...") sesuai dengan lokasi file .ttf Anda
// dan build system (Gradle) dikonfigurasi untuk menyertakan resources.

@Composable
fun robotoFontFamily(): FontFamily {
    // Gunakan @Composable karena pemuatan resource mungkin async di beberapa platform
    return FontFamily(
        Font(Res.font.roboto_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
        Font(Res.font.roboto_semibold, weight = FontWeight.Medium, style = FontStyle.Normal),
        Font(Res.font.roboto_bold, weight = FontWeight.Bold, style = FontStyle.Normal)
        // Tambahkan varian lain (Italic, Black, Light, dll.) jika diperlukan dan file-nya ada
    )
}

// Anda bisa membuat font family terpisah jika display dan body menggunakan font berbeda
// Jika sama (seperti contoh awal Anda), cukup satu.
@Composable
fun appFontFamily() = robotoFontFamily() // Ganti jika font berbeda

// Default Material 3 typography values
val baseline = Typography()

// Definisikan AppTypography di dalam Composable jika font family perlu @Composable
// Atau buat font family sebagai konstanta jika tidak perlu @Composable (tergantung implementasi Font())
@Composable
fun createTypography(): Typography {
    val displayFontFamily = appFontFamily() // Panggil composable font family
    val bodyFontFamily = appFontFamily()    // Panggil composable font family

    return Typography(
        displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
        displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
        displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
        headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
        headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
        titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
        titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
        titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
        bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
        bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
        bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
        labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
        labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
        labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
    )
}

// Jika Anda ingin akses global non-composable, Anda mungkin perlu state hoisting
// atau menyediakan Typography melalui CompositionLocal. Untuk tema, biasanya dibuat
// saat `MaterialTheme` dipanggil.