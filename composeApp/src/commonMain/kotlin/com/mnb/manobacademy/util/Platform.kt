// --- File: composeApp/src/commonMain/kotlin/util/Platform.kt ---
// *** PASTIKAN FILE INI DAN IMPLEMENTASI ACTUAL-NYA ADA ***
package com.mnb.manobacademy.util // Sesuaikan package

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect enum class PlatformType {
    ANDROID, DESKTOP
}

expect val currentPlatform: PlatformType