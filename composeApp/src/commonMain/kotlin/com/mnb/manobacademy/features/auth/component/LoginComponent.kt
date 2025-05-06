package com.mnb.manobacademy.features.auth.component // Sesuaikan package

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.mnb.manobacademy.core.data.repository.AuthRepository // Import repository
import com.mnb.manobacademy.core.data.repository.AuthResult // Import AuthResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

interface LoginComponent {
    val state: Value<State>

    fun onEmailChanged(text: String)
    fun onPasswordChanged(text: String)
    fun onLoginClicked()
    fun onRegisterClicked()
    fun onForgotPasswordClicked()

    data class State(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val error: String? = null
    )
}

class DefaultLoginComponent(
    componentContext: ComponentContext,
    private val authRepository: AuthRepository, // Terima repository
    private val onNavigateToRegister: () -> Unit,
    private val onNavigateToForgotPassword: () -> Unit,
    private val onLoginSuccess: () -> Unit // Callback sukses
) : LoginComponent, ComponentContext by componentContext {

    private val _state = MutableValue(LoginComponent.State())
    override val state: Value<LoginComponent.State> = _state

    private val componentScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    override fun onEmailChanged(text: String) {
        _state.update { it.copy(email = text, error = null) } // Hapus error saat input berubah
    }

    override fun onPasswordChanged(text: String) {
        _state.update { it.copy(password = text, error = null) } // Hapus error saat input berubah
    }

    override fun onLoginClicked() {
        if (_state.value.isLoading) return // Hindari klik ganda saat loading

        val currentState = _state.value
        _state.update { it.copy(isLoading = true, error = null) } // Mulai loading

        componentScope.launch {
            val result = authRepository.signInWithEmail(currentState.email, currentState.password)
            when (result) {
                is AuthResult.Success -> {
                    println("Login successful!")
                    _state.update { it.copy(isLoading = false) }
                    onLoginSuccess() // Panggil callback navigasi sukses
                }
                is AuthResult.Error -> {
                    println("Login failed: ${result.message}")
                    _state.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    override fun onRegisterClicked() {
        onNavigateToRegister() // Panggil callback navigasi
    }

    override fun onForgotPasswordClicked() {
        onNavigateToForgotPassword() // Panggil callback navigasi
    }

    init {
        lifecycle.doOnDestroy {
            componentScope.cancel() // Batalkan coroutine saat komponen hancur
        }
    }
}
