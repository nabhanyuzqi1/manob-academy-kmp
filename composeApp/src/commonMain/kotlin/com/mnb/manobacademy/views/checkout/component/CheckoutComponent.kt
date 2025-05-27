package com.mnb.manobacademy.views.checkout.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.mnb.manobacademy.models.BookingItem
import com.mnb.manobacademy.views.booking.component.DefaultBookingComponent

interface CheckoutComponent {
    val state: Value<State>

    fun onBackClicked()
    fun onNavigateToPaymentMethod()

    data class State(
        val itemsToCheckout: List<BookingItem> = emptyList(),
        val totalAmount: Double = 0.0,
        val currentStep: Int = 0 // 0: Checkout, 1: Payment, 2: Done (di sini selalu 0 untuk UI ini)
    )
}

class DefaultCheckoutComponent(
    componentContext: ComponentContext,
    private val itemsFromBooking: List<BookingItem>, // Terima item dari BookingScreen
    private val onNavigateBack: () -> Unit,
    private val onNavigateToPaymentScreen: (List<BookingItem>, Double) -> Unit // Kirim item & total
) : CheckoutComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        CheckoutComponent.State(
            itemsToCheckout = itemsFromBooking,
            totalAmount = itemsFromBooking.sumOf { it.price },
            currentStep = 0 // Step "Checkout" aktif di layar ini
        )
    )
    override val state: Value<CheckoutComponent.State> = _state

    override fun onBackClicked() {
        onNavigateBack()
    }

    override fun onNavigateToPaymentMethod() {
        if (_state.value.itemsToCheckout.isNotEmpty()) {
            println("Navigating to Payment Method with total: ${DefaultBookingComponent.formatPrice(_state.value.totalAmount)}")
            onNavigateToPaymentScreen(_state.value.itemsToCheckout, _state.value.totalAmount)
        } else {
            println("No items to proceed to payment.")
            // Idealnya, tampilkan pesan ke pengguna
        }
    }
}