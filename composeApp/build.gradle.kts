import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler) // Plugin untuk compiler
    alias(libs.plugins.kotlinSerialization)
    // id("kotlin-parcelize") // Tidak perlu jika tidak pakai @Parcelize Essenty
}

// --- Baca local.properties ---
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
    try {
        localProperties.load(FileInputStream(localPropertiesFile))
    } catch (e: Exception) {
        println("Warning: Could not load local.properties: ${e.message}")
    }
} else {
    println("Warning: local.properties not found in root project. Supabase credentials might be missing.")
}
val supabaseUrl = localProperties.getProperty("supabase.url", "")
val supabaseAnonKey = localProperties.getProperty("supabase.anon.key", "")
if (supabaseUrl.isBlank() || supabaseAnonKey.isBlank()) {
    println("Warning: Supabase URL or Key is missing in local.properties. Build might fail or app might not connect.")
}
// ---------------------------


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
            }
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
                implementation(compose.materialIconsExtended)

                // Supabase
                implementation(libs.supabase.auth.kt)
                implementation(libs.supabase.postgrest.kt)
                implementation(libs.supabase.realtime.kt) // Uncomment jika pakai
                implementation(libs.supabase.storage.kt) // Uncomment jika pakai

                // Ktor Client
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.okhttp)

                // Decompose
                implementation(libs.decompose)
                implementation(libs.decompose.extensions.compose)

                // Logging
                implementation(libs.napier)

                // Serialization runtime
                implementation(libs.kotlinx.serialization.json)

                // Coroutines
                implementation(libs.kotlinx.coroutines.core)

                //dekstop tooling
                implementation(libs.ui.tooling.preview.desktop)
            }
        }
    }
}

android {
    namespace = "com.mnb.manobacademy" // <<< Pastikan ini benar
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.mnb.manobacademy"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "SUPABASE_URL", "\"$supabaseUrl\"")
        buildConfigField("String", "SUPABASE_ANON_KEY", "\"$supabaseAnonKey\"")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
        getByName("debug") {
            // Opsi debug
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
    // Pengaturan Compose Compiler Extension
    composeOptions {
        // Pastikan alias 'compose.compiler' ada di libs.versions.toml bagian [versions]
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get() // <<< Baris ini sudah benar
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
        mainClass = "com.mnb.manobacademy.MainKt" // Pastikan package dan nama file benar

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.mnb.manobacademy"
            packageVersion = "1.0.0"
        }
    }
}
