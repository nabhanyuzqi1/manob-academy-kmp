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
    val paddingSmall: Dp = 8.dp,
    val paddingMedium: Dp = 16.dp,
    val paddingLarge: Dp = 24.dp,
    val paddingExtraLarge: Dp = 32.dp,
    val paddingHuge: Dp = 40.dp,

    // Spacing (Jarak antar elemen)
    val spacingSmall: Dp = 4.dp,
    val spacingMedium: Dp = 8.dp,
    val spacingLarge: Dp = 16.dp,
    val spacingExtraLarge: Dp = 24.dp,
    val spacingHuge: Dp = 32.dp,
    val spacingMassive: Dp = 48.dp,
    val spacingGiant: Dp = 64.dp,

    // Ukuran Logo (Subjektif, sesuaikan sesuai desain)
    val logoSizeSmall: Dp = 100.dp,
    val logoSizeLarge: Dp = 120.dp,

    // Tombol & Elemen Sosial
    val buttonHeight: Dp = 48.dp,
    val socialButtonSize: Dp = 48.dp,
    val socialIconSize: Dp = 24.dp,
    val socialButtonSpacing: Dp = 12.dp,
    val socialButtonShapeRadius: Dp = 8.dp, // DISET ke 8.dp (sedikit radius)

    // Radius Sudut
    val textFieldCornerRadius: Dp = 4.dp, // DISET ke 4.dp (sedikit radius)
    val primaryButtonCornerRadius: Dp = 8.dp, // DISET ke 8.dp (sedikit radius)

    // New Added Dimens (from HomeScreen context)
    val cardCornerRadiusLarge: Dp = 16.dp,
    val cardCornerRadiusMedium: Dp = 12.dp,
    val cardElevation: Dp = 2.dp,
    val searchBarHeight: Dp = 56.dp, // Example, ensure this matches OutlinedTextField desired height
    val categoryChipPaddingHorizontal: Dp = 12.dp,
    val categoryChipPaddingVertical: Dp = 8.dp,
    val favoriteClassImageHeight: Dp = 200.dp, // Height for the image box in FavoriteCourseCard
    val classCardWidth: Dp = 180.dp,
    val classCardImageHeight: Dp = 100.dp,
    val instructorAvatarSize: Dp = 60.dp,
    val bottomNavIconSize: Dp = 24.dp,
    val spacingExtraSmall: Dp = 2.dp, // Added for finer control, e.g. in CourseCard


    // Lain-lain
    val dividerThickness: Dp = 1.dp,
    val desktopFormMaxWidth: Dp = 450.dp,
    val topSpacingMobile: Dp = 64.dp,
    val topSpacingDesktop: Dp = 40.dp,
    val bottomRowPaddingVertical: Dp = 16.dp,
    val formInternalPaddingVertical: Dp = 16.dp,

    // Splash Screen (Subjektif)
    val splashLogoSize: Dp = 200.dp,

    // Dimens Forgot Password Screen (Sesuaikan dengan layout)
    val illustrationSizeMedium: Dp = 140.dp,
    val illustrationSizeLarge: Dp = 160.dp,
    val selectionCardPadding: Dp = 16.dp,
    val selectionCardIconSize: Dp = 40.dp,
    val selectionCardIconPadding: Dp = 12.dp,
    val selectionCardCornerRadius: Dp = 8.dp, // TETAP (tidak diubah sesuai permintaan)
    val selectionCardSpacing: Dp = 16.dp,

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
    paddingSmall = 4.dp,
    paddingMedium = 8.dp,
    paddingLarge = 16.dp,
    paddingExtraLarge = 20.dp,
    paddingHuge = 24.dp,

    spacingSmall = 4.dp,
    spacingMedium = 8.dp,
    spacingLarge = 12.dp,
    spacingExtraLarge = 16.dp,
    spacingHuge = 24.dp,
    spacingMassive = 32.dp,
    spacingGiant = 48.dp,
    spacingExtraSmall = 2.dp,


    logoSizeSmall = 80.dp,
    logoSizeLarge = 100.dp,

    buttonHeight = 48.dp,
    socialButtonSize = 40.dp, // Ukuran visual bisa lebih kecil, tapi pastikan area sentuh 48dp
    socialIconSize = 20.dp,
    socialButtonSpacing = 8.dp,
    socialButtonShapeRadius = 8.dp, // DISET ke 8.dp (konsisten)

    textFieldCornerRadius = 4.dp, // DISET ke 4.dp (konsisten)
    primaryButtonCornerRadius = 8.dp, // DISET ke 8.dp (konsisten)

    // New Added Dimens
    cardCornerRadiusLarge = 12.dp,
    cardCornerRadiusMedium = 8.dp,
    cardElevation = 1.dp,
    searchBarHeight = 48.dp,
    categoryChipPaddingHorizontal = 8.dp,
    categoryChipPaddingVertical = 6.dp,
    favoriteClassImageHeight = 80.dp,
    classCardWidth = 150.dp,
    classCardImageHeight = 80.dp,
    instructorAvatarSize = 48.dp,
    bottomNavIconSize = 20.dp,

    dividerThickness = 1.dp,
    desktopFormMaxWidth = 400.dp, // Max width for forms on compact screens
    topSpacingMobile = 48.dp,
    topSpacingDesktop = 24.dp, // Kurang relevan di compact, tapi bisa untuk konsistensi
    bottomRowPaddingVertical = 12.dp,
    formInternalPaddingVertical = 12.dp,

    splashLogoSize = 180.dp,
    illustrationSizeMedium = 120.dp,
    illustrationSizeLarge = 140.dp,
    selectionCardPadding = 12.dp,
    selectionCardIconSize = 36.dp,
    selectionCardIconPadding = 8.dp,
    selectionCardCornerRadius = 8.dp, // TETAP (tidak diubah sesuai permintaan)
    selectionCardSpacing = 12.dp,

    guideLogoSize = 100.dp,
    guideIndicatorWidth = 20.dp,
    guideIndicatorHeight = 4.dp,
    guideIndicatorSpacing = 6.dp,
    guideTextPaddingBottom = 40.dp,
    guideButtonPaddingBottom = 24.dp
)

// Medium: Tablet portrait kecil, ponsel landscape (600-839dp)
// Menggunakan nilai default dari data class Dimensions yang sudah disesuaikan
val MediumDimens = Dimensions() // Uses the default values defined in the Dimensions data class

// Expanded: Tablet landscape, desktop (840dp+)
val ExpandedDimens = Dimensions(
    paddingSmall = 12.dp,
    paddingMedium = 24.dp,
    paddingLarge = 32.dp,
    paddingExtraLarge = 40.dp,
    paddingHuge = 48.dp,

    spacingSmall = 8.dp,
    spacingMedium = 12.dp,
    spacingLarge = 20.dp,
    spacingExtraLarge = 32.dp,
    spacingHuge = 40.dp,
    spacingMassive = 56.dp,
    spacingGiant = 72.dp,
    spacingExtraSmall = 4.dp,

    logoSizeSmall = 120.dp,
    logoSizeLarge = 150.dp,

    buttonHeight = 56.dp, // Sedikit lebih besar
    socialButtonSize = 56.dp, // Lebih besar
    socialIconSize = 28.dp, // Sedikit lebih besar
    socialButtonSpacing = 16.dp,
    socialButtonShapeRadius = 8.dp, // DISET ke 8.dp (konsisten)

    textFieldCornerRadius = 4.dp, // DISET ke 4.dp (konsisten)
    primaryButtonCornerRadius = 8.dp, // DISET ke 8.dp (konsisten)

    // New Added Dimens
    cardCornerRadiusLarge = 20.dp,
    cardCornerRadiusMedium = 16.dp,
    cardElevation = 3.dp,
    searchBarHeight = 64.dp,
    categoryChipPaddingHorizontal = 16.dp,
    categoryChipPaddingVertical = 10.dp,
    favoriteClassImageHeight = 120.dp,
    classCardWidth = 220.dp, // Wider cards for expanded screens
    classCardImageHeight = 120.dp,
    instructorAvatarSize = 72.dp,
    bottomNavIconSize = 28.dp,

    dividerThickness = 1.dp,
    desktopFormMaxWidth = 500.dp, // Max width for forms on larger screens
    topSpacingMobile = 80.dp, // Kurang relevan, utamakan topSpacingDesktop
    topSpacingDesktop = 56.dp,
    bottomRowPaddingVertical = 24.dp,
    formInternalPaddingVertical = 24.dp,

    splashLogoSize = 250.dp,
    illustrationSizeMedium = 160.dp, // Slightly larger illustrations
    illustrationSizeLarge = 200.dp,
    selectionCardPadding = 20.dp,
    selectionCardIconSize = 48.dp,
    selectionCardIconPadding = 16.dp,
    selectionCardCornerRadius = 12.dp, // Bisa disesuaikan untuk Expanded, atau TETAP jika desain konsisten
    selectionCardSpacing = 20.dp,

    guideLogoSize = 120.dp, // Konsisten dengan Medium atau sedikit lebih besar
    guideIndicatorWidth = 32.dp,
    guideIndicatorHeight = 5.dp,
    guideIndicatorSpacing = 10.dp,
    guideTextPaddingBottom = 56.dp,
    guideButtonPaddingBottom = 40.dp
)


/**
 * CompositionLocal untuk menyediakan instance Dimensions ke seluruh pohon Composable.
 * Defaultnya menggunakan MediumDimens. Akan di-override oleh ProvideDimens.
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

/**
 * Composable function to provide the appropriate Dimensions based on the window size class.
 * This should be used at a high level in your app's theme, typically wrapping the main content.
 *
 * @param windowWidthSizeClass The current window width size class of the device.
 * @param content The composable content that will have access to the provided dimensions.
 *
 * Example Usage in your AppTheme:
 * ```
 * val windowSizeClass = calculateWindowSizeClass(activity = LocalContext.current as Activity)
 * ProvideDimens(windowWidthSizeClass = windowSizeClass.widthSizeClass) {
 * // Your app's Scaffold and content
 * }
 * ```
 */
