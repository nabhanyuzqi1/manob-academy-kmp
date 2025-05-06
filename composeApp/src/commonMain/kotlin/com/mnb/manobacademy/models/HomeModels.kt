// Contoh di features/home/data/model/HomeModels.kt
package com.mnb.manobacademy.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // Import ikon yang relevan
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import manobacademykmp.composeapp.generated.resources.* // Import Res

data class Category(val nameRes: StringResource, val icon: ImageVector)
data class Course(
    val id: String,
    val title: String,
    val category: String,
    val rating: Float,
    val originalPrice: String,
    val discountedPrice: String,
    val imageUrl: String // Ganti dengan DrawableResource jika gambar lokal
)
data class Instructor(val id: String, val name: String, val imageUrl: String)
data class FavoriteCourse(
    val id: String,
    val title: String,
    val instructor: String,
    val rating: Float,
    val lectures: Int,
    val enrolled: Int,
    val imageUrl: String
)

// --- Data Model untuk Berita (News) ---
// TODO: This data class should be in your models package
data class NewsItem(
    val id: String,
    val title: String,
    val source: String,
    val date: String,
    val imageUrl: String // Placeholder for image resource/URL
)

// Contoh data dummy (bisa dihapus nanti)
val dummyCategories = listOf(
    Category(Res.string.home_category_art, Icons.Default.Palette),
    Category(Res.string.home_category_photography, Icons.Default.PhotoCamera),
    Category(Res.string.home_category_design, Icons.Default.Draw),
    Category(Res.string.home_category_video, Icons.Default.Videocam)
)
val dummyFavorite = FavoriteCourse("fav1", "Fotografi Live Class", "Warhub Parmungkas", 4.8f, 25, 48, "")
val dummyCourses = listOf(
    Course("c1", "Kelas Pemula Fotografi", "Fotografi", 4.7f, "Rp 90.000", "Rp 44.900", ""),
    Course("c2", "Desain Grafis Dasar", "Desain", 4.9f, "Rp 120.000", "Rp 59.900", ""),
    Course("c3", "Editing Video Mobile", "Video", 4.6f, "Rp 80.000", "Rp 39.900", "")
)
val dummyInstructors = listOf(
    Instructor("i1", "Instruktur A", ""),
    Instructor("i2", "Instruktur B", ""),
    Instructor("i3", "Instruktur C", ""),
    Instructor("i4", "Instruktur D", "")
)

// TODO: This dummy list should be replaced by data from HomeComponent.state.newsItems
val dummyNewsItems = listOf(
    NewsItem("n1", "Peluncuran Fitur AI di Manob Academy!", "Blog Manob Academy", "5 Mei 2025", ""),
    NewsItem("n2", "Tips Produktif Belajar Online", "Info Edukasi", "4 Mei 2025", ""),
    NewsItem("n3", "Diskon Spesial Kursus Desain", "Promo Terkini", "3 Mei 2025", ""),
    NewsItem("n4", "Wawancara Eksklusif dengan Instruktur Populer", "Manob Spotlight", "2 Mei 2025", "")
)

// Data untuk Bottom Navigation
sealed class BottomNavItem(val route: String, val titleRes: StringResource, val icon: ImageVector) {
    data object Home : BottomNavItem("home", Res.string.home_bottom_nav_home, Icons.Filled.Home)
    data object Classes : BottomNavItem("classes", Res.string.home_bottom_nav_classes, Icons.Filled.School) // Ganti ikon jika perlu
    data object Checkout : BottomNavItem("checkout", Res.string.home_bottom_nav_checkout, Icons.Filled.BookmarkBorder) // Ganti ikon jika perlu
    data object Dashboard : BottomNavItem("dashboard", Res.string.home_bottom_nav_dashboard, Icons.Filled.GridView) // Ganti ikon jika perlu
    data object Profile : BottomNavItem("profile", Res.string.home_bottom_nav_profile, Icons.Filled.Person)
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Classes,
    BottomNavItem.Checkout,
    BottomNavItem.Dashboard,
    BottomNavItem.Profile
)

