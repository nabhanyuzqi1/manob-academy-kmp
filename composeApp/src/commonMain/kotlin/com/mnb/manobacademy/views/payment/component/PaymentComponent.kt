package com.mnb.manobacademy.views.payment.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.mnb.manobacademy.models.BookingItem
import com.mnb.manobacademy.models.PaymentMethod
import com.mnb.manobacademy.models.getDummyPaymentMethods
import com.mnb.manobacademy.views.booking.component.DefaultBookingComponent

interface PaymentComponent {
    val state: Value<State>

    fun onBackClicked()
    fun onPaymentMethodSelected(methodId: String)
    fun onPayNowClicked()

    data class State(
        val itemsToPay: List<BookingItem> = emptyList(),
        val totalAmount: Double = 0.0,
        val paymentMethods: List<PaymentMethod> = emptyList(),
        val selectedPaymentMethodId: String? = null,
        val currentStep: Int = 1 // 0: Checkout, 1: Payment, 2: Done (di sini selalu 1)
    )
}

class DefaultPaymentComponent(
    componentContext: ComponentContext,
    private val itemsFromCheckout: List<BookingItem>,
    private val totalAmountFromCheckout: Double,
    private val onNavigateBack: () -> Unit,
    private val onPaymentSuccess: () -> Unit // Navigasi ke layar "Done" atau Home
) : PaymentComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        PaymentComponent.State(
            itemsToPay = itemsFromCheckout,
            totalAmount = totalAmountFromCheckout,
            paymentMethods = getDummyPaymentMethods(),
            currentStep = 1 // Step "Payment" aktif
        )
    )
    override val state: Value<PaymentComponent.State> = _state

    override fun onBackClicked() {
        onNavigateBack()
    }

    override fun onPaymentMethodSelected(methodId: String) {
        _state.update { currentState ->
            val updatedMethods = currentState.paymentMethods.map {
                it.copy(isSelected = it.id == methodId)
            }
            currentState.copy(
                paymentMethods = updatedMethods,
                selectedPaymentMethodId = methodId
            )
        }
    }

    override fun onPayNowClicked() {
        if (_state.value.selectedPaymentMethodId != null) {
            val selectedMethod = _state.value.paymentMethods.find { it.id == _state.value.selectedPaymentMethodId }
            println("Pay Now clicked. Amount: ${DefaultBookingComponent.formatPrice(_state.value.totalAmount)}, Method: ${selectedMethod?.name}")
            // TODO: Implementasi logika pembayaran
            // Setelah sukses:
            // _state.update { it.copy(currentStep = 2) } // Jika "Done" di screen yang sama
            onPaymentSuccess() // Navigasi ke layar konfirmasi/selesai
        } else {
            println("No payment method selected.")
            // Idealnya, tampilkan pesan ke pengguna (Snackbar, Toast, atau pesan di UI)
        }
    }
}