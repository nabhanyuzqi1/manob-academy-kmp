package com.mnb.manobacademy.views.auth.component // Sesuaikan package

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

// Enum untuk pilihan metode reset
enum class ResetMethod {
    SMS, EMAIL, NONE
}

// Interface untuk komponen logika Lupa Kata Sandi
interface ForgotPasswordComponent {
    val state: Value<State> // State yang bisa diobservasi oleh UI

    fun onMethodSelected(method: ResetMethod)
    fun onResetClicked()
    fun onBackClicked()

    // Data class untuk state UI
    data class State(
        val selectedMethod: ResetMethod = ResetMethod.NONE,
        val maskedPhoneNumber: String? = "+62 8524xxxxx", // Ambil dari data user nanti
        val maskedEmail: String? = "nabhanxxxxx@gmail.com", // Ambil dari data user nanti
        val isResetEnabled: Boolean = false,
        val isLoading: Boolean = false
    )
}

// Implementasi default
class DefaultForgotPasswordComponent(
    componentContext: ComponentContext,
    private val onNavigateBack: () -> Unit,
    private val onResetInitiated: (ResetMethod) -> Unit // Callback saat reset dimulai
) : ForgotPasswordComponent, ComponentContext by componentContext {

    private val _state = MutableValue(ForgotPasswordComponent.State())
    override val state: Value<ForgotPasswordComponent.State> = _state

    private val componentScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    override fun onMethodSelected(method: ResetMethod) {
        _state.update { it.copy(selectedMethod = method, isResetEnabled = true) }
    }

    override fun onResetClicked() {
        if (!_state.value.isResetEnabled || _state.value.isLoading) return

        val selectedMethod = _state.value.selectedMethod
        if (selectedMethod != ResetMethod.NONE) {
            _state.update { it.copy(isLoading = true) }
            println("Reset initiated via: $selectedMethod")

            // Simulasi proses reset
            componentScope.launch(Dispatchers.Default) {
                try {
                    // TODO: Panggil UseCase/Repository untuk memulai proses reset password
                    kotlinx.coroutines.delay(1500) // Simulasi network call

                    launch(Dispatchers.Main.immediate) {
                        _state.update { it.copy(isLoading = false) }
                        // Panggil callback untuk navigasi atau menampilkan pesan sukses
                        onResetInitiated(selectedMethod)
                    }
                } catch (e: Exception) {
                    println("Reset Error: ${e.message}")
                    launch(Dispatchers.Main.immediate) {
                        _state.update { it.copy(isLoading = false) }
                        // TODO: Tampilkan error ke user
                    }
                }
            }
        }
    }

    override fun onBackClicked() {
        onNavigateBack()
    }

    init {
        lifecycle.doOnDestroy {
            componentScope.cancel()
        }
    }
}
