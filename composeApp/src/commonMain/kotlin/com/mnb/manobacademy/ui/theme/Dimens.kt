package com.mnb.manobacademy.ui.theme // <- Sesuaikan package

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Data class untuk menampung nilai-nilai dimensi yang umum digunakan dalam aplikasi.
 */
data class Dimensions(
    val paddingSmall: Dp = 4.dp,
    val paddingMedium: Dp = 8.dp,
    val paddingLarge: Dp = 16.dp,
    val paddingExtraLarge: Dp = 24.dp,
    val paddingHuge: Dp = 32.dp,

    val spacingSmall: Dp = 4.dp,
    val spacingMedium: Dp = 8.dp,
    val spacingLarge: Dp = 16.dp,
    val spacingExtraLarge: Dp = 24.dp,
    val spacingHuge: Dp = 32.dp,
    val spacingMassive: Dp = 48.dp,
    val spacingGiant: Dp = 64.dp,

    val logoSizeSmall: Dp = 100.dp,
    val logoSizeLarge: Dp = 120.dp,

    val buttonHeight: Dp = 50.dp,
    val socialButtonSize: Dp = 50.dp,
    val socialIconSize: Dp = 24.dp,
    val socialButtonSpacing: Dp = 16.dp,
    val socialButtonShapeRadius: Dp = 20.dp,

    val textFieldCornerRadius: Dp = 30.dp,
    val primaryButtonCornerRadius: Dp = 50.dp,

    val dividerThickness: Dp = 1.dp,

    val desktopFormMaxWidth: Dp = 450.dp,

    // Tambahkan dimensi lain sesuai kebutuhan
    val topSpacingMobile: Dp = 80.dp,
    val topSpacingDesktop: Dp = 40.dp,
    val bottomRowPaddingVertical: Dp = 20.dp,
    val formInternalPaddingVertical: Dp = 16.dp,


    //SPLASH SCREEN
    val splashLogoSize: Dp = 200.dp,

    // Dimens Forgot Password Screen
    val illustrationSizeMedium: Dp = 180.dp, // Ukuran ilustrasi
    val illustrationSizeLarge: Dp = 240.dp, // Ukuran ilustrasi
    val selectionCardPadding: Dp = 16.dp,
    val selectionCardIconSize: Dp = 40.dp, // Ukuran ikon di dalam kartu
    val selectionCardIconPadding: Dp = 12.dp,
    val selectionCardCornerRadius: Dp = 20.dp,
    val selectionCardSpacing: Dp = 16.dp,

    // Guide Screen Dimensions
    val guideLogoSize: Dp = 80.dp, // Ukuran logo di guide screen
    val guideIndicatorWidth: Dp = 24.dp,
    val guideIndicatorHeight: Dp = 4.dp,
    val guideIndicatorSpacing: Dp = 8.dp,
    val guideTextPaddingBottom: Dp = 48.dp, // Jarak teks ke indikator/tombol
    val guideButtonPaddingBottom: Dp = 32.dp, // Jarak tombol dari bawah
)

/**
 * CompositionLocal untuk menyediakan instance Dimensions ke seluruh pohon Composable.
 */
val LocalDimens = compositionLocalOf { Dimensions() }