package com.mnb.manobacademy.core.data.repository // Atau package data/repository Anda

// UserSession masih merupakan tipe data yang relevan dari plugin Auth (sebelumnya GoTrue)
import io.github.jan.supabase.auth.user.UserSession

/**
 * Interface untuk mengelola operasi autentikasi.
 * Menggunakan plugin Supabase Auth.
 */
interface AuthRepository {
    /**
     * Mencoba login pengguna dengan email dan password.
     * @return AuthResult yang berisi data sesi atau error.
     */
    suspend fun signInWithEmail(email: String, password: String): AuthResult<UserSession>

    /**
     * Mencoba mendaftarkan pengguna baru dengan email dan password.
     * @return AuthResult yang berisi data sesi atau error.
     */
    suspend fun signUpWithEmail(email: String, password: String): AuthResult<UserSession> // Tambahkan parameter lain jika perlu (nama, dll.)

    /**
     * Mengeluarkan pengguna dari sesi saat ini.
     */
    suspend fun signOut()

    /**
     * Mengamati perubahan status autentikasi (login/logout).
     * Anda mungkin perlu membuat enum/sealed class AuthState sendiri.
     */
    // fun observeAuthState(): Flow<AuthState> // Implementasi ini bisa lebih kompleks

    /**
     * Mendapatkan sesi pengguna saat ini jika ada.
     */
    // suspend fun getCurrentSession(): UserSession? // Atau cara lain mendapatkan info user
}

// Data class sederhana untuk hasil operasi auth
// Anda bisa membuatnya lebih detail sesuai kebutuhan
sealed class AuthResult<out T> {
    data class Success<T>(val data: T) : AuthResult<T>()
    data class Error(val message: String, val cause: Throwable? = null) : AuthResult<Nothing>()
}
