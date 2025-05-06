package com.mnb.manobacademy.features.auth.component // Sesuaikan package jika perlu

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Interface untuk komponen logika layar verifikasi kode OTP.
 */
interface VerificationCodeComponent {
    /**
     * Alamat email tempat kode OTP dikirim (untuk ditampilkan di UI).
     * Bisa null jika tidak tersedia.
     */
    val emailAddress: String?

    /**
     * Dipanggil ketika pengguna mengklik tombol Verifikasi.
     * @param code Kode OTP yang dimasukkan pengguna.
     */
    fun onVerifyClicked(code: String)

    /**
     * Dipanggil ketika pengguna mengklik link Kirim Ulang.
     */
    fun onResendClicked()

    /**
     * Dipanggil ketika pengguna menekan tombol kembali di TopAppBar.
     * (Meskipun navigasi ditangani oleh Root, ini bisa berguna untuk logika internal jika diperlukan).
     */
    fun onBackClicked()
}

/**
 * Implementasi default untuk [VerificationCodeComponent].
 *
 * @param componentContext Konteks Decompose untuk komponen ini.
 * @param email Alamat email yang akan ditampilkan (opsional).
 * @param onVerified Callback yang dipanggil ketika verifikasi OTP berhasil.
 * @param onNavigateBack Callback yang dipanggil untuk menavigasi kembali.
 */
class DefaultVerificationCodeComponent(
    componentContext: ComponentContext,
    val email: String?, // Terima email saat pembuatan
    private val onVerified: () -> Unit, // Callback saat berhasil verifikasi
    private val onNavigateBack: () -> Unit // Callback untuk navigasi kembali
) : VerificationCodeComponent, ComponentContext by componentContext { // Delegasikan ComponentContext

    // Buat CoroutineScope untuk tugas-tugas background (misalnya, verifikasi)
    // Gunakan SupervisorJob agar kegagalan satu job tidak membatalkan yang lain
    // Gunakan Dispatchers.Main.immediate untuk update UI yang aman jika diperlukan dari background
    private val componentScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    // Implementasi properti dari interface
    override val emailAddress: String? = email

    /**
     * Menangani klik tombol Verifikasi.
     * Memulai proses verifikasi kode OTP.
     */
    override fun onVerifyClicked(code: String) {
        println("Verification attempt: Code='$code', Email='$emailAddress'")
        // TODO: Tampilkan indikator loading di UI (misalnya melalui StateFlow)

        // Jalankan logika verifikasi di background thread
        componentScope.launch(Dispatchers.Default) { // Gunakan Dispatchers.Default untuk CPU-bound task
            try {
                // --- TODO: Implementasikan logika verifikasi di sini ---
                // Contoh: Panggil UseCase atau Repository
                // val isSuccess = verifyOtpUseCase(emailAddress, code)
                delay(1500) // Simulasi network delay
                val isSuccess = code == "123456" // Contoh logika sukses sederhana

                // Kembali ke Main thread untuk navigasi/update UI
                launch(Dispatchers.Main.immediate) {
                    if (isSuccess) {
                        println("Verification Successful!")
                        onVerified() // Panggil callback sukses
                    } else {
                        println("Verification Failed!")
                        // TODO: Tampilkan pesan error di UI (misalnya melalui StateFlow)
                    }
                    // TODO: Sembunyikan indikator loading
                }
            } catch (e: Exception) {
                // Tangani error (misalnya, masalah jaringan)
                println("Verification Error: ${e.message}")
                launch(Dispatchers.Main.immediate) {
                    // TODO: Tampilkan pesan error di UI
                    // TODO: Sembunyikan indikator loading
                }
            }
        }
    }

    /**
     * Menangani klik link Kirim Ulang.
     * Memulai proses pengiriman ulang kode OTP.
     */
    override fun onResendClicked() {
        println("Resend code requested for Email='$emailAddress'")
        // TODO: Tampilkan indikator loading/feedback di UI

        componentScope.launch(Dispatchers.Default) {
            try {
                // --- TODO: Implementasikan logika kirim ulang kode di sini ---
                // Contoh: Panggil UseCase atau Repository
                // val resendSuccess = resendOtpUseCase(emailAddress)
                delay(1000) // Simulasi network delay
                val resendSuccess = true // Contoh sukses

                launch(Dispatchers.Main.immediate) {
                    if (resendSuccess) {
                        println("Resend Successful!")
                        // TODO: Tampilkan pesan sukses/feedback di UI (misalnya, "Kode baru telah dikirim")
                    } else {
                        println("Resend Failed!")
                        // TODO: Tampilkan pesan error di UI
                    }
                    // TODO: Sembunyikan indikator loading/feedback
                }
            } catch (e: Exception) {
                println("Resend Error: ${e.message}")
                launch(Dispatchers.Main.immediate) {
                    // TODO: Tampilkan pesan error di UI
                    // TODO: Sembunyikan indikator loading/feedback
                }
            }
        }
    }

    /**
     * Menangani klik tombol kembali.
     * Memanggil callback navigasi kembali yang disediakan oleh parent (RootComponent).
     */
    override fun onBackClicked() {
        onNavigateBack()
    }

    // Penting untuk membatalkan scope saat komponen dihancurkan
    // untuk menghindari memory leak
    init {
        lifecycle.doOnDestroy {
            componentScope.cancel() // Batalkan semua coroutine yang berjalan
            println("VerificationCodeComponent destroyed, scope cancelled.")
        }
    }
}
