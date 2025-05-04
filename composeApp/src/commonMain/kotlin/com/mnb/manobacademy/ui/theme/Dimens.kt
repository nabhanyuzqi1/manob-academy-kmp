package com.mnb.manobacademy.ui.theme // <- Sesuaikan package Anda


import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


// --- Data Class Dimensions (Struktur sama, nilai default sebagai basis Medium) ---
// Nilai default ini akan kita gunakan sebagai titik awal untuk MediumDimens
data class Dimensions(
    // Padding
    val paddingSmall: Dp = 8.dp,      // M3: Umumnya 8dp untuk padding kecil/sedang
    val paddingMedium: Dp = 16.dp,     // M3: Umumnya 16dp untuk padding standar
    val paddingLarge: Dp = 24.dp,     // M3: Umumnya 24dp untuk padding besar
    val paddingExtraLarge: Dp = 32.dp, // M3: Skala berikutnya
    val paddingHuge: Dp = 40.dp,      // M3: Skala berikutnya

    // Spacing (Jarak antar elemen)
    val spacingSmall: Dp = 4.dp,       // M3: Spasi terkecil
    val spacingMedium: Dp = 8.dp,      // M3: Spasi umum
    val spacingLarge: Dp = 16.dp,     // M3: Spasi lebih besar
    val spacingExtraLarge: Dp = 24.dp, // M3: Spasi besar
    val spacingHuge: Dp = 32.dp,      // M3: Skala berikutnya
    val spacingMassive: Dp = 48.dp,    // M3: Skala berikutnya
    val spacingGiant: Dp = 64.dp,     // M3: Skala berikutnya

    // Ukuran Logo (Subjektif, sesuaikan sesuai desain)
    val logoSizeSmall: Dp = 100.dp,
    val logoSizeLarge: Dp = 120.dp,

    // Tombol & Elemen Sosial
    val buttonHeight: Dp = 48.dp,         // M3: Tinggi umum untuk tombol (memenuhi target sentuh)
    val socialButtonSize: Dp = 48.dp,     // M3: Pastikan ukuran target sentuh minimal 48x48
    val socialIconSize: Dp = 24.dp,     // M3: Ukuran ikon standar
    val socialButtonSpacing: Dp = 12.dp,  // M3: Spasi antar tombol sosial
    val socialButtonShapeRadius: Dp = 24.dp, // M3: Radius penuh untuk tombol lingkaran

    // Radius Sudut
    val textFieldCornerRadius: Dp = 12.dp, // M3: Radius umum untuk text field (Large)
    val primaryButtonCornerRadius: Dp = 24.dp, // M3: Radius penuh untuk tombol (setengah tinggi)

    // Lain-lain
    val dividerThickness: Dp = 1.dp,      // M3: Standar
    val desktopFormMaxWidth: Dp = 450.dp, // Spesifik aplikasi
    val topSpacingMobile: Dp = 64.dp,     // Sesuaikan sesuai kebutuhan layout mobile
    val topSpacingDesktop: Dp = 40.dp,    // Sesuaikan sesuai kebutuhan layout desktop
    val bottomRowPaddingVertical: Dp = 16.dp, // Padding bawah umum
    val formInternalPaddingVertical: Dp = 16.dp, // Padding dalam form

    // Splash Screen (Subjektif)
    val splashLogoSize: Dp = 200.dp,

    // Dimens Forgot Password Screen (Sesuaikan dengan layout)
    val illustrationSizeMedium: Dp = 140.dp,
    val illustrationSizeLarge: Dp = 160.dp,
    val selectionCardPadding: Dp = 16.dp,
    val selectionCardIconSize: Dp = 40.dp, // Ukuran ikon dalam kartu
    val selectionCardIconPadding: Dp = 12.dp, // Padding ikon dalam kartu
    val selectionCardCornerRadius: Dp = 16.dp, // M3: Radius Large untuk kartu
    val selectionCardSpacing: Dp = 16.dp, // Jarak antar kartu

    // Guide Screen Dimensions (Sesuaikan dengan layout)
    val guideLogoSize: Dp = 120.dp,
    val guideIndicatorWidth: Dp = 24.dp,
    val guideIndicatorHeight: Dp = 4.dp,
    val guideIndicatorSpacing: Dp = 8.dp,
    val guideTextPaddingBottom: Dp = 48.dp,
    val guideButtonPaddingBottom: Dp = 32.dp,
)

// --- Set Dimensi untuk Ukuran Layar Berbeda (Disesuaikan dengan M3) ---

// Compact: Layar ponsel kecil (0-599dp)
val CompactDimens = Dimensions(
    paddingSmall = 4.dp, // Lebih rapat
    paddingMedium = 8.dp,
    paddingLarge = 16.dp,
    paddingExtraLarge = 20.dp,
    paddingHuge = 24.dp,

    spacingSmall = 4.dp,
    spacingMedium = 8.dp,
    spacingLarge = 12.dp, // Lebih rapat
    spacingExtraLarge = 16.dp,
    spacingHuge = 24.dp,
    spacingMassive = 32.dp,
    spacingGiant = 48.dp,

    logoSizeSmall = 80.dp,
    logoSizeLarge = 100.dp,

    buttonHeight = 48.dp, // Tetap 48dp untuk target sentuh
    socialButtonSize = 40.dp, // Ukuran visual bisa lebih kecil, tapi pastikan area sentuh 48dp
    socialIconSize = 20.dp,
    socialButtonSpacing = 8.dp,
    socialButtonShapeRadius = 20.dp, // Radius penuh (setengah ukuran visual)

    textFieldCornerRadius = 8.dp, // M3: Radius Small
    primaryButtonCornerRadius = 24.dp, // M3: Radius penuh (setengah tinggi)

    dividerThickness = 1.dp,
    desktopFormMaxWidth = 400.dp, // Tidak relevan di compact, tapi set
    topSpacingMobile = 48.dp, // Lebih rapat
    topSpacingDesktop = 24.dp, // Tidak relevan di compact
    bottomRowPaddingVertical = 12.dp, // Lebih rapat (sesuaikan user code)
    formInternalPaddingVertical = 12.dp, // Lebih rapat

    splashLogoSize = 180.dp,
    illustrationSizeMedium = 120.dp,
    illustrationSizeLarge = 140.dp, // Sesuaikan user code
    selectionCardPadding = 12.dp,
    selectionCardIconSize = 36.dp,
    selectionCardIconPadding = 8.dp,
    selectionCardCornerRadius = 12.dp, // M3: Radius Medium
    selectionCardSpacing = 12.dp,

    guideLogoSize = 100.dp,
    guideIndicatorWidth = 20.dp,
    guideIndicatorHeight = 4.dp,
    guideIndicatorSpacing = 6.dp,
    guideTextPaddingBottom = 40.dp,
    guideButtonPaddingBottom = 24.dp
)

// Medium: Tablet portrait kecil, ponsel landscape (600-839dp)
// Menggunakan nilai default dari data class Dimensions yang sudah disesuaikan M3
val MediumDimens = Dimensions()

// Expanded: Tablet landscape, desktop (840dp+)
val ExpandedDimens = Dimensions(
    paddingSmall = 12.dp, // Lebih lega
    paddingMedium = 24.dp,
    paddingLarge = 32.dp,
    paddingExtraLarge = 40.dp,
    paddingHuge = 48.dp,

    spacingSmall = 8.dp, // Sedikit lebih lega
    spacingMedium = 12.dp,
    spacingLarge = 20.dp,
    spacingExtraLarge = 32.dp,
    spacingHuge = 40.dp,
    spacingMassive = 56.dp,
    spacingGiant = 72.dp,

    logoSizeSmall = 120.dp,
    logoSizeLarge = 150.dp,

    buttonHeight = 56.dp, // Sedikit lebih besar
    socialButtonSize = 56.dp, // Lebih besar
    socialIconSize = 28.dp, // Sedikit lebih besar
    socialButtonSpacing = 16.dp,
    socialButtonShapeRadius = 28.dp, // Radius penuh

    textFieldCornerRadius = 16.dp, // M3: Radius Large
    primaryButtonCornerRadius = 28.dp, // M3: Radius penuh

    dividerThickness = 1.dp,
    desktopFormMaxWidth = 500.dp, // Lebih lebar untuk desktop/tablet besar
    topSpacingMobile = 80.dp, // Tidak relevan
    topSpacingDesktop = 56.dp, // Lebih lega
    bottomRowPaddingVertical = 24.dp, // Lebih lega (sesuaikan user code)
    formInternalPaddingVertical = 24.dp, // Lebih lega

    splashLogoSize = 250.dp,
    illustrationSizeMedium = 140.dp, // Sesuaikan user code
    illustrationSizeLarge = 180.dp, // Sesuaikan user code
    selectionCardPadding = 20.dp,
    selectionCardIconSize = 48.dp,
    selectionCardIconPadding = 16.dp,
    selectionCardCornerRadius = 20.dp, // Radius lebih besar
    selectionCardSpacing = 20.dp,

    guideLogoSize = 100.dp,
    guideIndicatorWidth = 32.dp,
    guideIndicatorHeight = 5.dp,
    guideIndicatorSpacing = 10.dp,
    guideTextPaddingBottom = 56.dp,
    guideButtonPaddingBottom = 40.dp
)


/**
 * CompositionLocal untuk menyediakan instance Dimensions ke seluruh pohon Composable.
 * Defaultnya menggunakan MediumDimens, akan di-override berdasarkan WindowSizeClass.
 */
val LocalDimens = compositionLocalOf { MediumDimens }

/**
 * Properti helper untuk mengakses Dimensions saat ini dari CompositionLocal.
 * Penggunaan: `val dimens = AppDimens.current` di dalam @Composable
 */
val AppDimens: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current

