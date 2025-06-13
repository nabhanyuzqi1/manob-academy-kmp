package com.mnb.manobacademy.views.auth.component // Sesuaikan package jika perlu

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
     * Current state of the verification UI
     */
    val state: Value<VerificationState>

    /**
     * Email address where OTP code was sent
     */
    val emailAddress: String?

    /**
     * Called when verify button is clicked
     */
    fun onVerifyClicked(code: String)

    /**
     * Called when resend link is clicked
     */
    fun onResendClicked()

    /**
     * Called when back button is pressed
     */
    fun onBackClicked()

    /**
     * Called to dismiss any error messages
     */
    fun onErrorDismissed()
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

        componentScope.launch(Dispatchers.Default) {
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

        _state.update { it.copy(isLoading = true, error = null) }

        resendJob?.cancel()
        resendJob = componentScope.launch(Dispatchers.Default) {
            try {
                val result = verificationRepository.resendCode(email ?: "")
                withContext(Dispatchers.Main.immediate) {
                    when (result) {
                        is ResendResult.Success -> {
                            _state.update { 
                                it.copy(
                                    isLoading = false,
                                    successMessage = "Verification code resent",
                                    isResendEnabled = false
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
