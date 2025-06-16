package com.mnb.manobacademy.views.auth.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.mnb.manobacademy.models.VerificationState
import kotlinx.coroutines.*

// --- Definisi State dan Result yang Diperlukan ---
// HAPUS 'data class VerificationState' DARI SINI

/**
 * Hasil dari operasi verifikasi kode.
 */
sealed class VerificationResult {
    data object Success : VerificationResult()
    data object Invalid : VerificationResult()
    data class Error(val message: String) : VerificationResult()
}

/**
 * Hasil dari operasi kirim ulang kode.
 */
sealed class ResendResult {
    data object Success : ResendResult()
    data class Error(val message: String) : ResendResult()
}

/**
 * Dummy repository untuk tujuan kompilasi. Ganti dengan implementasi nyata Anda.
 */
interface VerificationRepository {
    suspend fun verifyCode(email: String, code: String): VerificationResult
    suspend fun resendCode(email: String): ResendResult
}


// --- Interface dan Implementasi Komponen ---

/**
 * Interface untuk komponen logika layar verifikasi kode OTP.
 */
interface VerificationCodeComponent {
    val state: Value<VerificationState>
    val emailAddress: String?

    fun onVerifyClicked(code: String)
    fun onResendClicked()
    fun onBackClicked()
    fun onErrorDismissed()
}

/**
 * Implementasi default untuk [VerificationCodeComponent].
 */
class DefaultVerificationCodeComponent(
    componentContext: ComponentContext,
    val email: String?,
    private val onVerified: () -> Unit,
    private val onNavigateBack: () -> Unit,
    private val verificationRepository: VerificationRepository
) : VerificationCodeComponent, ComponentContext by componentContext {

    private val _state = MutableValue(VerificationState())
    override val state: Value<VerificationState> = _state
    override val emailAddress: String? = email

    private val componentScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
    private var resendJob: Job? = null

    override fun onVerifyClicked(code: String) {
        if (_state.value.isLoading) return

        _state.update { it.copy(isLoading = true, error = null) }

        componentScope.launch {
            try {
                val result = verificationRepository.verifyCode(email ?: "", code)
                withContext(Dispatchers.Main.immediate) {
                    when (result) {
                        is VerificationResult.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    verificationSuccess = true,
                                    successMessage = "Verification successful"
                                )
                            }
                            onVerified()
                        }
                        is VerificationResult.Invalid -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = "Invalid verification code"
                                )
                            }
                        }
                        is VerificationResult.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.message
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main.immediate) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "An unexpected error occurred"
                        )
                    }
                }
            }
        }
    }

    override fun onResendClicked() {
        if (_state.value.isLoading || !_state.value.isResendEnabled) return

        _state.update { it.copy(isLoading = true, error = null, successMessage = null) }

        resendJob?.cancel()
        resendJob = componentScope.launch {
            try {
                val result = verificationRepository.resendCode(email ?: "")
                withContext(Dispatchers.Main.immediate) {
                    when (result) {
                        is ResendResult.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    successMessage = "Verification code resent"
                                )
                            }
                            startResendCooldown()
                        }
                        is ResendResult.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.message
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main.immediate) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Failed to resend code"
                        )
                    }
                }
            }
        }
    }

    override fun onBackClicked() {
        onNavigateBack()
    }

    override fun onErrorDismissed() {
        _state.update { it.copy(error = null) }
    }

    private fun startResendCooldown() {
        componentScope.launch {
            _state.update { it.copy(isResendEnabled = false) }
            var countdown = 60 // 60 second cooldown
            while (countdown > 0) {
                _state.update { it.copy(resendCooldown = countdown) }
                delay(1000)
                countdown--
            }
            _state.update {
                it.copy(
                    isResendEnabled = true,
                    resendCooldown = 0
                )
            }
        }
    }

    init {
        lifecycle.doOnDestroy {
            componentScope.cancel()
            resendJob?.cancel()
        }
    }
}