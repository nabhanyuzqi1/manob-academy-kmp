package com.mnb.manobacademy.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // Import ikon yang relevan
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import org.jetbrains.compose.resources.StringResource
import manobacademykmp.composeapp.generated.resources.* // Import Res

@Parcelize // Tambahkan Parcelize
data class Category(val nameRes: StringResource, val icon: ImageVector) : Parcelable // Implement Parcelable

@Parcelize // Tambahkan Parcelize
data class Course(
    val id: String,
    val title: String,
    val category: String,
    val rating: Float,
    val originalPrice: String,
    val discountedPrice: String,
    val imageUrl: String // Ganti dengan DrawableResource jika gambar lokal
) : Parcelable // Implement Parcelable

@Parcelize // Tambahkan Parcelize
data class Instructor(val id: String, val name: String, val imageUrl: String) : Parcelable // Implement Parcelable

@Parcelize // Tambahkan Parcelize
data class FavoriteCourse(
    val id: String,
    val title: String,
    val instructor: String,
    val rating: Float,
    val lectures: Int,
    val enrolled: Int,
    val imageUrl: String
) : Parcelable // Implement Parcelable

@Parcelize // Tambahkan Parcelize
data class NewsItem(
    val id: String,
    val title: String,
    val source: String,
    val date: String,
    val imageUrl: String // Placeholder for image resource/URL
) : Parcelable // Implement Parcelable

@Parcelize // Tambahkan Parcelize
data class BookingItem(
    val id: String,
    val imageUrl: String? = null, // Nantinya bisa String URL gambar
    val title: String,
    val schedule: String,
    val price: Double,
    val priceFormatted: String, // e.g., "Rp.90.000"
    var isSelected: Boolean = true
) : Parcelable // Implement Parcelable

@Parcelize // Tambahkan Parcelize
data class PaymentMethod(
    val id: String,
    val name: String,
    val category: PaymentCategory,
    val iconUrl: String? = null, // URL untuk ikon online
    // val iconRes: DrawableResource? = null // Untuk ikon lokal dari KMP resources
    val isSelected: Boolean = false
) : Parcelable // Implement Parcelable

@Parcelize // Tambahkan Parcelize
enum class PaymentCategory : Parcelable { // Implement Parcelable
    CREDIT_DEBIT,
    BANK_TRANSFER,
    E_MONEY,
    OTC,
    OTHER
}

// Contoh data dummy (bisa dihapus nanti)
val dummyCategories = listOf(
    Category(Res.string.home_category_art, Icons.Default.Palette),
    Category(Res.string.home_category_photography, Icons.Default.PhotoCamera),
    Category(Res.string.home_category_design, Icons.Default.Draw),
    Category(Res.string.home_category_video, Icons.Default.Videocam)
)
// ... (dummy data lainnya tetap sama) ...

fun getDummyBookingItems(): List<BookingItem> {
    return listOf(
        BookingItem("item1", null, "Fotografi Newbie", "Senin, 20 April 2025", 90000.0, "Rp.90.000", true),
        BookingItem("item2", null, "Fotografi Newbie", "Senin, 20 April 2025", 90000.0, "Rp.90.000", false),
        BookingItem("item3", null, "Workshop Audio", "Senin, 20 April 2025", 90000.0, "Rp.90.000", true),
        BookingItem("item4", null, "Fotografi Newbie", "Senin, 20 April 2025", 90000.0, "Rp.90.000", true),
        BookingItem("item5", null, "Fotografi Newbie", "Senin, 20 April 2025", 90000.0, "Rp.90.000", false)
    )
}

fun getDummyPaymentMethods(): List<PaymentMethod> {
    return listOf(
        PaymentMethod("visa", "VISA", PaymentCategory.CREDIT_DEBIT, iconUrl = "https://placehold.co/60x40/E91E63/FFFFFF?text=VISA&font=roboto"),
        PaymentMethod("mastercard", "Mastercard", PaymentCategory.CREDIT_DEBIT, iconUrl = "https://placehold.co/60x40/2196F3/FFFFFF?text=MC&font=roboto"),
        PaymentMethod("jcb", "JCB", PaymentCategory.CREDIT_DEBIT, iconUrl = "https://placehold.co/60x40/4CAF50/FFFFFF?text=JCB&font=roboto"),
        PaymentMethod("amex", "AMEX", PaymentCategory.CREDIT_DEBIT, iconUrl = "https://placehold.co/60x40/FF9800/FFFFFF?text=AMEX&font=roboto"),
        PaymentMethod("bca", "BCA", PaymentCategory.BANK_TRANSFER, iconUrl = "https://placehold.co/60x40/03A9F4/FFFFFF?text=BCA&font=roboto"),
        PaymentMethod("mandiri", "Mandiri", PaymentCategory.BANK_TRANSFER, iconUrl = "https://placehold.co/60x40/FFC107/FFFFFF?text=MDR&font=roboto"),
        PaymentMethod("bri", "BRI", PaymentCategory.BANK_TRANSFER, iconUrl = "https://placehold.co/60x40/795548/FFFFFF?text=BRI&font=roboto"),
        PaymentMethod("bni", "BNI", PaymentCategory.BANK_TRANSFER, iconUrl = "https://placehold.co/60x40/607D8B/FFFFFF?text=BNI&font=roboto"),
        PaymentMethod("linkaja", "LinkAja", PaymentCategory.E_MONEY, iconUrl = "https://placehold.co/60x40/F44336/FFFFFF?text=LA&font=roboto"),
        PaymentMethod("ovo", "OVO", PaymentCategory.E_MONEY, iconUrl = "https://placehold.co/60x40/9C27B0/FFFFFF?text=OVO&font=roboto"),
        PaymentMethod("shopeepay", "ShopeePay", PaymentCategory.E_MONEY, iconUrl = "https://placehold.co/60x40/FF5722/FFFFFF?text=SP&font=roboto"),
        PaymentMethod("gopay", "GoPay", PaymentCategory.E_MONEY, iconUrl = "https://placehold.co/60x40/00BCD4/FFFFFF?text=GP&font=roboto"),
        PaymentMethod("dana", "DANA", PaymentCategory.E_MONEY, iconUrl = "https://placehold.co/60x40/8BC34A/FFFFFF?text=DANA&font=roboto"),
        PaymentMethod("indomaret", "Indomaret", PaymentCategory.OTC, iconUrl = "https://placehold.co/60x40/FFEB3B/000000?text=INDO&font=roboto"),
        PaymentMethod("alfamart", "Alfamart", PaymentCategory.OTC, iconUrl = "https://placehold.co/60x40/E91E63/FFFFFF?text=ALFA&font=roboto"),
        PaymentMethod("qris", "QRIS", PaymentCategory.OTHER, iconUrl = "https://placehold.co/60x40/000000/FFFFFF?text=QRIS&font=roboto")
    )
}


// Data untuk Bottom Navigation
// Pastikan route untuk Checkout sesuai dengan ScreenConfig yang akan memulai alur booking
sealed class BottomNavItem(val route: String, val titleRes: StringResource, val icon: ImageVector) {
    data object Home : BottomNavItem("home_config", Res.string.home_bottom_nav_home, Icons.Filled.Home) // Ganti route jika perlu
    data object Classes : BottomNavItem("classes_config", Res.string.home_bottom_nav_classes, Icons.Filled.School)
    data object BookingFlow : BottomNavItem("booking_flow_config", Res.string.home_bottom_nav_checkout, Icons.Filled.ShoppingCartCheckout) // Mengarah ke awal alur booking
    data object Dashboard : BottomNavItem("dashboard_config", Res.string.home_bottom_nav_dashboard, Icons.Filled.GridView)
    data object Profile : BottomNavItem("profile_config", Res.string.home_bottom_nav_profile, Icons.Filled.Person)
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Classes,
    BottomNavItem.BookingFlow, // Menggunakan item baru untuk alur booking
    BottomNavItem.Dashboard,
    BottomNavItem.Profile
)
