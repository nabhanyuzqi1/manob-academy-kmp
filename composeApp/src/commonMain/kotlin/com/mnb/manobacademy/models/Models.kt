package com.mnb.manobacademy.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable
import manobacademykmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource

data class Category(val nameRes: StringResource, val icon: ImageVector)

@Serializable
data class Course(
    val id: String,
    val title: String,
    val category: String,
    val rating: Float,
    val originalPrice: String,
    val discountedPrice: String,
    val imageUrl: String
)

@Serializable
data class Instructor(val id: String, val name: String, val imageUrl: String)

@Serializable
data class FavoriteCourse(
    val id: String,
    val title: String,
    val instructor: String,
    val rating: Float,
    val lectures: Int,
    val enrolled: Int,
    val imageUrl: String
)

// --- PERBAIKAN 1: Tambahkan properti 'source' di sini ---
@Serializable
data class NewsItem(
    val id: String,
    val title: String,
    val source: String, // Properti yang hilang ditambahkan di sini
    val date: String,
    val imageUrl: String
)

@Serializable
data class BookingItem(
    val id: String,
    val imageUrl: String? = null,
    val title: String,
    val schedule: String,
    val price: Double,
    val priceFormatted: String,
    var isSelected: Boolean = true
)

@Serializable
data class PaymentMethod(
    val id: String,
    val name: String,
    val category: PaymentCategory,
    val iconUrl: String? = null,
    val isSelected: Boolean = false
)

@Serializable
enum class PaymentCategory {
    CREDIT_DEBIT,
    BANK_TRANSFER,
    E_MONEY,
    OTC,
    OTHER
}

// --- BAGIAN DATA DUMMY ---

val dummyCategories = listOf(
    Category(Res.string.home_category_art, Icons.Default.Palette),
    Category(Res.string.home_category_photography, Icons.Default.PhotoCamera),
    Category(Res.string.home_category_design, Icons.Default.Draw),
    Category(Res.string.home_category_video, Icons.Default.Videocam)
)

// --- PERBAIKAN 2: Tambahkan data untuk 'source' di dummy data ---
val dummyNewsItems = listOf(
    NewsItem("news1", "Peluncuran Kursus Baru: Fotografi Drone", "Manob News", "15 Juni 2025", ""),
    NewsItem("news2", "Diskon 50% Untuk Kursus Desain Grafis", "Blog Manob", "14 Juni 2025", ""),
    NewsItem("news3", "Tips & Trik Menguasai Adobe Premiere Pro", "Manob News", "12 Juni 2025", "")
)

val dummyCourses = listOf(
    Course("c1", "Dasar-dasar UI/UX Design", "Desain", 4.8f, "Rp 250.000", "Rp 125.000", ""),
    Course("c2", "Pemrograman Kotlin untuk Pemula", "Development", 4.9f, "Rp 300.000", "Rp 150.000", ""),
    Course("c3", "Sinematografi dengan Smartphone", "Video", 4.7f, "Rp 200.000", "Rp 100.000", ""),
    Course("c4", "Fotografi Produk untuk Bisnis Online", "Fotografi", 4.8f, "Rp 220.000", "Rp 110.000", "")
)

val dummyInstructors = listOf(
    Instructor("i1", "Budi Santoso", ""),
    Instructor("i2", "Citra Lestari", ""),
    Instructor("i3", "Rian Hidayat", "")
)

val dummyFavorite = FavoriteCourse(
    id = "fav1",
    title = "Belajar Menguasai Adobe Illustrator",
    instructor = "Andi Wijaya",
    rating = 4.9f,
    lectures = 25,
    enrolled = 1204,
    imageUrl = ""
)

// --- FUNGSI DATA DUMMY ---

fun getDummyBookingItems(): List<BookingItem> {
    return listOf(
        BookingItem("item1", null, "Fotografi Newbie", "Senin, 20 April 2025", 90000.0, "Rp.90.000", true),
        BookingItem("item2", null, "Workshop Editing Video", "Selasa, 21 April 2025", 150000.0, "Rp.150.000", false),
        BookingItem("item3", null, "Workshop Audio", "Rabu, 22 April 2025", 120000.0, "Rp.120.000", true)
    )
}

fun getDummyPaymentMethods(): List<PaymentMethod> {
    return listOf(
        PaymentMethod("visa", "VISA", PaymentCategory.CREDIT_DEBIT, iconUrl = "https://placehold.co/60x40/E91E63/FFFFFF?text=VISA&font=roboto"),
        PaymentMethod("mastercard", "Mastercard", PaymentCategory.CREDIT_DEBIT, iconUrl = "https://placehold.co/60x40/2196F3/FFFFFF?text=MC&font=roboto"),
        PaymentMethod("bca", "BCA", PaymentCategory.BANK_TRANSFER, iconUrl = "https://placehold.co/60x40/03A9F4/FFFFFF?text=BCA&font=roboto"),
        PaymentMethod("ovo", "OVO", PaymentCategory.E_MONEY, iconUrl = "https://placehold.co/60x40/9C27B0/FFFFFF?text=OVO&font=roboto"),
        PaymentMethod("gopay", "GoPay", PaymentCategory.E_MONEY, iconUrl = "https://placehold.co/60x40/00BCD4/FFFFFF?text=GP&font=roboto"),
        PaymentMethod("indomaret", "Indomaret", PaymentCategory.OTC, iconUrl = "https://placehold.co/60x40/FFEB3B/000000?text=INDO&font=roboto")
    )
}

// Data untuk Bottom Navigation
sealed class BottomNavItem(val route: String, val titleRes: StringResource, val icon: ImageVector) {
    data object Home : BottomNavItem("home_config", Res.string.home_bottom_nav_home, Icons.Filled.Home)
    data object Classes : BottomNavItem("classes_config", Res.string.home_bottom_nav_classes, Icons.Filled.School)
    data object BookingFlow : BottomNavItem("booking_flow_config", Res.string.home_bottom_nav_checkout, Icons.Filled.ShoppingCartCheckout)
    data object Dashboard : BottomNavItem("dashboard_config", Res.string.home_bottom_nav_dashboard, Icons.Filled.GridView)
    data object Profile : BottomNavItem("profile_config", Res.string.home_bottom_nav_profile, Icons.Filled.Person)
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Classes,
    BottomNavItem.BookingFlow,
    BottomNavItem.Dashboard,
    BottomNavItem.Profile
)