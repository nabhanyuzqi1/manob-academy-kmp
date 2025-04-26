package com.mnb.manobacademy.util

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual enum class PlatformType {
    ANDROID, DESKTOP
}

actual val currentPlatform: PlatformType = PlatformType.DESKTOP // Sesuaikan dengan platform yang sesuai