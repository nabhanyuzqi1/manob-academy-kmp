package com.mnb.manobacademy.core.data.repository

sealed class VerificationResult {
    object Success : VerificationResult()
    object Invalid : VerificationResult()
    data class Error(val message: String) : VerificationResult()
}

sealed class ResendResult {
    object Success : ResendResult()
    data class Error(val message: String) : ResendResult()
}

interface VerificationRepository {
    /**
     * Verifies the OTP code for the given email
     */
    suspend fun verifyCode(email: String, code: String): VerificationResult

    /**
     * Requests a new OTP code to be sent to the given email
     */
    suspend fun resendCode(email: String): ResendResult
}
