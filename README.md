# Manob Academy - Aplikasi Pembelajaran Online (KMP)

Aplikasi pembelajaran online lintas platform (Android & Desktop) dibangun dengan Kotlin Multiplatform (KMP) dan Compose Multiplatform.

## ✨ Fitur

* Splash Screen & Guide/Onboarding (3 halaman swipeable)
* Autentikasi: Login, Registrasi, Lupa Kata Sandi (UI), Verifikasi OTP (UI)
* Home Screen Dasar (dengan Logout)
* Navigasi: Decompose
* Tema: Material 3, Mode Terang/Gelap Otomatis, Warna Dinamis (Android 12+), Resource Terpusat (Dimensi, String)
* UI Lintas Platform (Compose)
* Tampilan Edge-to-Edge (Android)

## 💻 Teknologi

* Kotlin, Compose Multiplatform, Decompose, Kotlin Coroutines
* Material 3, Gradle Kotlin DSL
* Android: AndroidX, Splash Screen API, WindowInsets

## 📂 Struktur Proyek

* `/composeApp`: Modul utama KMP.
    * `commonMain`: Kode & resource bersama (logika, UI, tema, navigasi, fitur).
    * `androidMain`: Kode & resource spesifik Android (`actual`, `MainActivity`, `res`).
    * `desktopMain`: Kode & resource spesifik Desktop (`actual`, `main()`).

## 🚀 Setup

* **Prasyarat:** Android Studio (terbaru), JDK 17+.
* **Android:** Buka di Android Studio, pilih `composeApp`, pilih device, klik Run.
* **Desktop:** Buka di Android Studio/IntelliJ, cari `main()` di `desktopMain`, klik Run.

Pelajari lebih lanjut tentang [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html).
