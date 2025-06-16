package com.mnb.manobacademy.models

/**
 * Represents the UI state for verification screens
 */
data class VerificationState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isResendEnabled: Boolean = true,
    val resendCooldown: Int = 0,
    val verificationSuccess: Boolean = false,
    val successMessage: String? = null
)