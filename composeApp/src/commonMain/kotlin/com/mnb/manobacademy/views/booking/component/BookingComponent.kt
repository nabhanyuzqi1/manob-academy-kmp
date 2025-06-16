package com.mnb.manobacademy.views.booking.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.mnb.manobacademy.models.BookingItem
import com.mnb.manobacademy.models.BottomNavItem
import com.mnb.manobacademy.models.getDummyBookingItems
import java.text.NumberFormat
import java.util.Locale

interface BookingComponent {
    val state: Value<State>

    fun onBackClicked()
    fun onFavoriteClicked()
    fun onItemCheckedChanged(itemId: String, isChecked: Boolean)
    fun onCheckoutClicked()
    fun onBottomNavItemSelected(newRoute: String)

    data class State(
        val bookingItems: List<BookingItem> = emptyList(),
        val subtotal: Double = 0.0,
        val currentStep: Int = 0, // 0: Checkout, 1: Payment, 2: Done
        // --- PERBAIKAN DI SINI ---
        // Ganti Checkout menjadi BookingFlow sesuai definisi di Models.kt
        val currentBottomNavRoute: String = BottomNavItem.BookingFlow.route,
        val isFavorite: Boolean = false // Contoh state untuk tombol favorit
    )
}

class DefaultBookingComponent(
    componentContext: ComponentContext,
    private val onNavigateBack: () -> Unit,
    private val onNavigateToPayment: (List<BookingItem>) -> Unit, // Kirim item yang di-checkout
    private val onNavigateToDifferentTab: (String) -> Unit // Untuk navigasi antar tab utama
) : BookingComponent, ComponentContext by componentContext {

    private val _state = MutableValue(BookingComponent.State(bookingItems = getDummyBookingItems()))
    override val state: Value<BookingComponent.State> = _state

    init {
        calculateSubtotal() // Hitung subtotal awal
    }

    override fun onBackClicked() {
        onNavigateBack()
    }

    override fun onFavoriteClicked() {
        _state.update { it.copy(isFavorite = !it.isFavorite) }
        println("Favorite button clicked, new state: ${_state.value.isFavorite}")
    }

    override fun onItemCheckedChanged(itemId: String, isChecked: Boolean) {
        _state.update { currentState ->
            val updatedItems = currentState.bookingItems.map { item ->
                if (item.id == itemId) item.copy(isSelected = isChecked) else item
            }
            currentState.copy(bookingItems = updatedItems)
        }
        calculateSubtotal()
    }

    private fun calculateSubtotal() {
        _state.update { currentState ->
            val newSubtotal = currentState.bookingItems
                .filter { it.isSelected }
                .sumOf { it.price }
            currentState.copy(subtotal = newSubtotal)
        }
    }

    override fun onCheckoutClicked() {
        val selectedItems = _state.value.bookingItems.filter { it.isSelected }
        if (selectedItems.isNotEmpty()) {
            println("Checkout clicked, Subtotal: ${formatPrice(_state.value.subtotal)}, Items: ${selectedItems.joinToString { it.title }}")
            onNavigateToPayment(selectedItems)
        } else {
            println("No items selected for checkout.")
        }
    }

    override fun onBottomNavItemSelected(newRoute: String) {
        if (newRoute == _state.value.currentBottomNavRoute) return

        _state.update { it.copy(currentBottomNavRoute = newRoute) }
        onNavigateToDifferentTab(newRoute)
    }

    companion object {
        fun formatPrice(price: Double): String {
            val locale = try {
                Locale("in", "ID")
            } catch (e: Exception) {
                Locale.US // Fallback
            }
            val currencyFormat = NumberFormat.getCurrencyInstance(locale)
            currencyFormat.maximumFractionDigits = 0
            var formatted = currencyFormat.format(price)
            if (locale.country == "ID") {
                formatted = formatted.replace("Rp", "Rp.")
            }
            return formatted
        }
    }
}