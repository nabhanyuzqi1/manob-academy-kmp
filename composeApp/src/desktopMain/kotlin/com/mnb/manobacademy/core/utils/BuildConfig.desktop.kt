package com.mnb.manobacademy.core.utils // Package HARUS sama dengan expect

import java.io.File
import java.io.FileInputStream
import java.util.Properties

/**
 * Implementasi actual untuk Desktop.
 * Membaca flag mode development langsung dari file local.properties
 * di root proyek saat runtime.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual val isDevelopmentMode: Boolean by lazy { // Gunakan lazy agar pembacaan file hanya sekali
    val properties = Properties()
    var devMode = false // Default ke false (Production)
    println("DEBUG: BuildKonfig.desktop.kt: Attempting to read app.mode from local.properties...")

    // Cari file local.properties relatif terhadap working directory
    val possiblePaths = listOf("local.properties", "../local.properties")
    var localPropertiesFile: File? = null

    for (path in possiblePaths) {
        val file = File(path)
        // println("DEBUG: Checking for local.properties at: ${file.absolutePath}") // Optional: uncomment for detailed path logging
        if (file.exists() && file.isFile) {
            localPropertiesFile = file
            println("DEBUG: Found local.properties at: ${file.absolutePath}")
            break
        }
    }

    if (localPropertiesFile != null) {
        try {
            FileInputStream(localPropertiesFile).use { input ->
                properties.load(input)
                val appMode = properties.getProperty("app.mode", "PRODUCTION") // Default ke PRODUCTION
                devMode = appMode.equals("DEVELOPMENT", ignoreCase = true)
                println("DEBUG: Loaded app.mode from ${localPropertiesFile.name}: '$appMode'. isDevelopmentMode set to: $devMode")
            }
        } catch (e: Exception) {
            println("ERROR: Failed to load local.properties for app.mode: ${e.message}")
        }
    } else {
        println("ERROR: local.properties not found in expected locations! Defaulting isDevelopmentMode to false.")
        println("DEBUG: Current working directory: ${System.getProperty("user.dir")}")
    }

    devMode // Kembalikan nilai yang sudah ditentukan
}

// Hapus atau komentari implementasi sebelumnya
// internal actual val isDevelopmentMode: Boolean = true
// internal actual val isDevelopmentMode: Boolean = System.getProperty("app.mode", "PRODUCTION").equals("DEVELOPMENT", ignoreCase = true)
