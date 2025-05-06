package com.mnb.manobacademy.core.network.supabase // Package HARUS sama dengan expect

import java.io.File // <<< Import File
import java.io.FileInputStream // <<< Import FileInputStream
import java.util.Properties // <<< Import Properties

/**
 * Implementasi actual untuk Desktop.
 * Membaca kredensial langsung dari file local.properties di root proyek saat runtime.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual object SupabaseCredentials {

    private val properties: Properties = Properties()
    private var urlFromProperties: String? = null
    private var keyFromProperties: String? = null

    init {
        println("DEBUG: SupabaseCredentials.desktop.kt init (local.properties Method)")
        // Cari file local.properties relatif terhadap working directory
        // Coba beberapa path umum (root, atau satu level di atas jika working dir adalah composeApp)
        val possiblePaths = listOf("local.properties", "../local.properties")
        var localPropertiesFile: File? = null

        for (path in possiblePaths) {
            val file = File(path)
            println("DEBUG: Checking for local.properties at: ${file.absolutePath}")
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
                    urlFromProperties = properties.getProperty("supabase.url")
                    keyFromProperties = properties.getProperty("supabase.anon.key")
                    println("DEBUG: Loaded properties from ${localPropertiesFile.name}.")
                }
            } catch (e: Exception) {
                println("ERROR: Failed to load local.properties for Desktop: ${e.message}")
                e.printStackTrace()
            }
        } else {
            println("ERROR: local.properties not found in expected locations relative to working directory!")
            // Print working directory untuk debug
            println("DEBUG: Current working directory: ${System.getProperty("user.dir")}")
        }
    }

    // Ambil nilai dari properties, berikan default jika gagal load atau key tidak ada
    actual val SUPABASE_URL: String = urlFromProperties ?: "" // Default ke string kosong jika gagal
    actual val SUPABASE_ANON_KEY: String = keyFromProperties ?: "" // Default ke string kosong jika gagal

    // --- Logging Tambahan ---
    init {
        println("DEBUG:   Final SUPABASE_URL: $SUPABASE_URL")
        println("DEBUG:   Final SUPABASE_ANON_KEY: ${SUPABASE_ANON_KEY.take(10)}...")
        if (SUPABASE_URL.isBlank()) {
            println("ERROR: Final SUPABASE_URL is blank!")
        }
        if (SUPABASE_ANON_KEY.isBlank()) {
            println("ERROR: Final SUPABASE_ANON_KEY is blank!")
        }
    }
    // -----------------------
}

