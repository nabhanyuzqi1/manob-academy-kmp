package com.mnb.manobacademy.core.utils // Package HARUS sama dengan expect

import com.mnb.manobacademy.BuildConfig // Import BuildConfig

/**
 * Implementasi actual untuk Android, mengambil nilai dari BuildConfig.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual val isDevelopmentMode: Boolean = BuildConfig.IS_DEVELOPMENT_MODE
    