@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.mnb.manobacademy.util

actual enum class PlatformType {
    ANDROID, DESKTOP
}

actual val currentPlatform: PlatformType = PlatformType.ANDROID // Sesuaikan dengan platform yang sesuai