import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties
import java.io.FileInputStream
import java.io.FileOutputStream // Import untuk task generate (jika menggunakan metode resource)

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler) // Plugin untuk compiler
    alias(libs.plugins.kotlinSerialization)
}

// --- Baca local.properties ---
val localProperties = Properties()
// Akses file dari root project, bukan dari composeApp
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
    try {
        localProperties.load(FileInputStream(localPropertiesFile))
    } catch (e: Exception) {
        println("Warning: Could not load local.properties: ${e.message}")
    }
} else {
    println("Warning: local.properties not found in root project. Using default values.")
}

// Ambil nilai atau gunakan default jika tidak ada
val supabaseUrl = localProperties.getProperty("supabase.url", "") // Beri default kosong atau URL dev jika perlu
val supabaseAnonKey = localProperties.getProperty("supabase.anon.key", "") // Beri default kosong atau key dev jika perlu
// <<< BACA FLAG APP MODE >>>
val appMode = localProperties.getProperty("app.mode", "PRODUCTION") // Default ke PRODUCTION jika tidak ada
val isDevelopmentMode = appMode.equals("DEVELOPMENT", ignoreCase = true)
// ---------------------------

// Validasi sederhana (opsional tapi bagus)
if (supabaseUrl.isBlank() || supabaseAnonKey.isBlank()) {
    println("Warning: Supabase URL or Key is missing in local.properties. Build might fail or app might not connect.")
}
println("Application Mode: $appMode (isDevelopmentMode: $isDevelopmentMode)") // Log mode
// ---------------------------

// Hapus task generate jika menggunakan dotenv untuk desktop
/*
tasks.register("generateSupabaseProperties") {
    // ... (definisi task generate resource) ...
}
*/

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11) // Sesuaikan jika perlu
        }
    }

    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11" // Sesuaikan jika perlu
        }
    }

    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing) // Atau coroutine engine lain
                // <<< Tambahkan dependensi dotenv-kotlin >>>
                implementation("io.github.cdimascio:dotenv-kotlin:6.4.1") // Cek versi terbaru

                // ---------------------------------------
            }
            // Hapus penambahan resource jika menggunakan dotenv
            // resources.srcDirs(tasks.named("generateSupabaseProperties").map { it.outputs.files })
        }

        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.core.splashscreen)
                implementation(libs.androidx.lifecycle.viewmodel.compose)
                implementation(libs.androidx.compose.material3.window.size) // Contoh alias
            }
        }
        val commonMain by getting {
            dependencies {
                // Compose
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended) // Untuk ikon seperti ChatBubbleOutline

                // Supabase
                implementation(libs.supabase.auth.kt)
                implementation(libs.supabase.postgrest.kt)
                implementation(libs.supabase.realtime.kt) // Uncomment jika pakai
                implementation(libs.supabase.storage.kt) // Uncomment jika pakai

                // Ktor Client
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.okhttp) // Engine umum untuk Android/JVM

                // Decompose
                implementation(libs.decompose)
                implementation(libs.decompose.extensions.compose)

                // Logging
                implementation(libs.napier)

                // Serialization runtime
                implementation(libs.kotlinx.serialization.json)

                // Coroutines
                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.essenty.parcelable)
                implementation(libs.coil.compose) // Tambahkan ini
                implementation(libs.coil.network.ktor) // Opsional, jika ingin menggunakan Ktor client dengan Coil
            }
        }
    }
}

android {
    namespace = "com.mnb.manobacademy" // <<< Pastikan ini benar
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.mnb.manobacademy"
        minSdk = libs.versions.android.minSdk.get().toInt() // Minimal 24 direkomendasikan Supabase
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        // --- Tambahkan BuildConfig Fields ---
        buildConfigField("String", "SUPABASE_URL", "\"$supabaseUrl\"")
        buildConfigField("String", "SUPABASE_ANON_KEY", "\"$supabaseAnonKey\"")
        // <<< TAMBAHKAN BUILD CONFIG UNTUK MODE DEVELOPMENT >>>
        buildConfigField("boolean", "IS_DEVELOPMENT_MODE", "$isDevelopmentMode")
        // -----------------------------------------------
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            // Anda bisa mendefinisikan BuildConfig field spesifik release di sini jika perlu
            // buildConfigField("boolean", "IS_DEVELOPMENT_MODE", "false")
        }
        getByName("debug") {
            // Opsi debug
            // Anda bisa mendefinisikan BuildConfig field spesifik debug di sini jika perlu
            // buildConfigField("boolean", "IS_DEVELOPMENT_MODE", "true") // Atau ambil dari local.properties seperti di atas
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    sourceSets["main"].apply {
        res.srcDirs("src/androidMain/res")
        resources.srcDirs("src/commonMain/resources")
    }

    // Dependensi spesifik Android
    dependencies {
        debugImplementation(libs.androidx.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "com.mnb.manobacademy.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.mnb.manobacademy"
            packageVersion = "1.0.0"
        }
    }
}

