// --- File: composeApp/build.gradle.kts ---
// *** PERBAIKAN DI SINI ***
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization) // Anda punya ini, bagus untuk Supabase/lainnya
    id("kotlin-parcelize") // Pastikan ini ada untuk Essenty jika diperbaiki nanti
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview) // Untuk @Preview Android
            implementation(libs.androidx.activity.compose)
            // implementation(libs.androidx.ui.tooling.preview.android) // Bisa dihapus jika compose.preview cukup
            // Tambahkan dependensi Android lain jika perlu
            implementation(libs.androidx.core.splashscreen) // Contoh jika pakai API Splash Android
            implementation(libs.androidx.lifecycle.viewmodel.compose) // ViewModel Compose
            implementation("androidx.compose.material3:material3-window-size-class:1.3.2") // Gunakan versi terbaru jika ada
        }
        commonMain.dependencies {
            // Compose UI Basics
            implementation(compose.runtime)
            implementation(compose.foundation)
            //implementation(compose.material) // Material 2
            implementation(compose.ui)
            implementation(compose.material3) // Jika pakai Material 3
            implementation(compose.components.resources) // Untuk resource KMP
            implementation(compose.materialIconsExtended) // Untuk


            // Preview Multiplatform (Gunakan alias yang benar dari TOML)
            implementation(libs.ui.tooling.preview.desktop)
            // Lifecycle ViewModel KMP (Hati-hati di Desktop, mungkin perlu MOKO atau Decompose state)
            // implementation(libs.androidx.lifecycle.viewmodel)

            // Supabase (Pastikan alias merujuk ke library yang benar: jan-tennert atau jan)
            // implementation(project.dependencies.platform("io.github.jan-tennert.supabase:bom:3.1.1")) // Contoh BOM Jan Tennert
            implementation(libs.supabase.auth.kt)
            implementation(libs.supabase.postgrest.kt)
            implementation(libs.supabase.realtime.kt)
            implementation(libs.supabase.storage.kt)

            // Ktor Client (Untuk Supabase atau network lain)
            implementation(libs.ktor.client.okhttp) // Atau client engine lain

            // Decompose
            implementation(libs.decompose)
            implementation(libs.decompose.extensions.compose) // Common compose extensions

            // MVIKotlin (Jika Anda memilihnya nanti)
            // implementation(libs.mvikotlin)
            // implementation(libs.mvikotlin.extensions.coroutines)

            // Logging
            implementation(libs.napier) // Jika menggunakan Napier

            // Essenty (jika ingin memperbaiki Parcelable)
            // api(libs.essenty.parcelable)

            // Hapus dependensi yang salah/tidak perlu dari sini:
            // implementation(libs.androidx.ui) // Ini biasanya tidak perlu di commonMain
            // implementation(libs.androidx.ui.tooling.preview) // Ini biasanya tidak perlu di commonMain
            // implementation(libs.androidx.material3.v112) // Pilih Material 2 atau 3
            // implementation(libs.ui.tooling.preview.desktop) // SALAH, jangan di commonMain
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs) // Penting untuk Desktop
            implementation(libs.kotlinx.coroutines.swing) // Coroutine Swing untuk Desktop
            // Hapus dependensi preview desktop dari sini

        }
    }
}

android {
    namespace = "com.mnb.manobacademy"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.mnb.manobacademy"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    // Aktifkan buildFeatures untuk Compose jika belum
    buildFeatures {
        compose = true
    }
    // Compose options (biasanya diatur oleh plugin composeMultiplatform)
    // composeOptions {
    //     kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    // }

    // Definisikan sourceSets untuk resources jika belum ada
    sourceSets["main"].apply {
        res.srcDirs("src/androidMain/res") // Resource khusus Android
        resources.srcDirs("src/commonMain/resources") // Resource bersama
    }
}
dependencies {
    debugImplementation(libs.androidx.ui.tooling)
}

// HAPUS BLOK DEPENDENCIES INI DARI composeApp/build.gradle.kts
// dependencies {
//     debugImplementation(compose.uiTooling) // Pindahkan ke androidMain jika perlu
// }

compose.desktop {
    application {
        mainClass = "com.mnb.manobacademy.MainKt" // Pastikan package benar

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.mnb.manobacademy"
            packageVersion = "1.0.0"
        }
    }
}