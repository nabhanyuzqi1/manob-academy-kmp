package com.mnb.manobacademy.core.utils // Package HARUS sama dengan expect

/**
 * Implementasi actual untuk Desktop.
 * Cara terbaik adalah membaca dari system property atau environment variable.
 * Untuk contoh ini, kita hardcode (sesuaikan untuk build development/production Anda).
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
//internal actual val isDevelopmentMode: Boolean = true // <<< Set ke true untuk development Desktop, false untuk rilis
// Atau baca dari system property:
internal actual val isDevelopmentMode: Boolean = System.getProperty("app.mode", "PRODUCTION").equals("DEVELOPMENT", ignoreCase = true)
    