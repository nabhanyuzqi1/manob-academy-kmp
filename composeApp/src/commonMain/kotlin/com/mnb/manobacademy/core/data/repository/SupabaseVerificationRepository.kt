package com.mnb.manobacademy.core.data.repository

import com.mnb.manobacademy.core.network.supabase.SupabaseManager
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

class SupabaseVerificationRepository : VerificationRepository {
    private val client = SupabaseManager.client
    private val timeoutMs = 30_000L // 30 seconds timeout

    override suspend fun verifyCode(email: String, code: String): VerificationResult {
        return withContext(Dispatchers.Default) {
            try {
                withTimeout(timeoutMs) {
                    // Implement Supabase verification logic here
                    // For now, we'll simulate with a basic check
                    if (code.length != 6) {
                        return@withTimeout VerificationResult.Invalid
                    }

                    try {
                        // Here you would typically call Supabase's verification endpoint
                        // client.auth.verifyOtp(email, code)
                        
                        // For testing, we'll consider "123456" as valid
                        if (code == "123456") {
                            VerificationResult.Success
                        } else {
                            VerificationResult.Invalid
                        }
                    } catch (e: Exception) {
                        VerificationResult.Error(e.message ?: "Failed to verify code")
                    }
                }
            } catch (e: Exception) {
                when (e) {
                    is kotlinx.coroutines.TimeoutCancellationException -> 
                        VerificationResult.Error("Verification request timed out")
                    else -> VerificationResult.Error(e.message ?: "An unexpected error occurred")
                }
            }
        }
    }

    override suspend fun resendCode(email: String): ResendResult {
        return withContext(Dispatchers.Default) {
            try {
                withTimeout(timeoutMs) {
                    try {
                        // Here you would typically call Supabase's resend endpoint
                        // client.auth.resendOtp(email)
                        
                        // For testing, we'll simulate success
                        ResendResult.Success
                    } catch (e: Exception) {
                        ResendResult.Error(e.message ?: "Failed to resend code")
                    }
                }
            } catch (e: Exception) {
                when (e) {
                    is kotlinx.coroutines.TimeoutCancellationException -> 
                        ResendResult.Error("Request timed out")
                    else -> ResendResult.Error(e.message ?: "An unexpected error occurred")
                }
            }
        }
    }
}
