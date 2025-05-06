package com.mnb.manobacademy.core.data.repository // Atau package data/repository Anda

import com.mnb.manobacademy.core.network.supabase.SupabaseManager
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Implementasi AuthRepository menggunakan Supabase Auth (GoTrue).
 */
class SupabaseAuthRepository : AuthRepository {

    private val client = SupabaseManager.client // Akses Supabase client

    override suspend fun signInWithEmail(email: String, password: String): AuthResult<UserSession> {
        return withContext(Dispatchers.Default) { // Jalankan di background thread
            try {
                // Panggil fungsi sign in dari Supabase Auth
                client.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }
                // Jika berhasil, ambil sesi saat ini (seharusnya sudah terupdate)
                val session = client.auth.currentSessionOrNull()
                if (session != null) {
                    AuthResult.Success(session)
                } else {
                    // Seharusnya tidak terjadi jika sign in berhasil, tapi sebagai fallback
                    AuthResult.Error("Gagal mendapatkan sesi setelah login.")
                }
            } catch (e: Exception) {
                // Tangani error dari Supabase atau jaringan
                println("Supabase SignIn Error: ${e.message}")
                AuthResult.Error(e.message ?: "Terjadi kesalahan saat login.", e)
            }
        }
    }

    override suspend fun signUpWithEmail(email: String, password: String): AuthResult<UserSession> {
        return withContext(Dispatchers.Default) {
            try {
                // Panggil fungsi sign up dari Supabase Auth
                client.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                    // Tambahkan data lain jika perlu:
                    // data = buildJsonObject { put("full_name", fullName) }
                }
                // Setelah sign up, Supabase biasanya otomatis login dan membuat sesi
                val session = client.auth.currentSessionOrNull()
                if (session != null) {
                    AuthResult.Success(session)
                } else {
                    // Mungkin perlu konfirmasi email? Atau error lain?
                    AuthResult.Error("Gagal mendapatkan sesi setelah daftar. Periksa email konfirmasi jika ada.")
                }
            } catch (e: Exception) {
                println("Supabase SignUp Error: ${e.message}")
                AuthResult.Error(e.message ?: "Terjadi kesalahan saat mendaftar.", e)
            }
        }
    }

    override suspend fun signOut() {
        withContext(Dispatchers.Default) {
            try {
                client.auth.signOut()
            } catch (e: Exception) {
                println("Supabase SignOut Error: ${e.message}")
                // Tangani error logout jika perlu (jarang terjadi)
            }
        }
    }

    // Implementasi observeAuthState() dan getCurrentSession() bisa ditambahkan nanti
    // Memerlukan pemahaman lebih dalam tentang Flow dan state management Supabase Auth
}
