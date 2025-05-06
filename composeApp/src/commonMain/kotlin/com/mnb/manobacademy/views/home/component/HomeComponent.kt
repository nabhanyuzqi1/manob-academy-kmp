package com.mnb.manobacademy.views.home.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.mnb.manobacademy.models.BottomNavItem
import com.mnb.manobacademy.models.Category
import com.mnb.manobacademy.models.Course
import com.mnb.manobacademy.models.FavoriteCourse
import com.mnb.manobacademy.models.Instructor
import com.mnb.manobacademy.models.dummyCategories
import com.mnb.manobacademy.models.dummyCourses
import com.mnb.manobacademy.models.dummyFavorite
import com.mnb.manobacademy.models.dummyInstructors

// Interface untuk komponen Home
interface HomeComponent {
    val state: Value<State> // State UI

    fun onSearchQueryChanged(query: String)
    fun onCategorySelected(category: Category)
    fun onCourseClicked(courseId: String)
    fun onInstructorClicked(instructorId: String)
    fun onBottomNavItemSelected(route: String)
    fun onLogoutClicked()

    // State untuk HomeScreen
    data class State(
        val userName: String = "Dryx Siregar", // Ambil dari data user nanti
        val searchQuery: String = "",
        val favoriteCourse: FavoriteCourse? = dummyFavorite, // Ganti dengan data asli
        val categories: List<Category> = dummyCategories,
        val selectedCategory: Category? = dummyCategories.firstOrNull(), // Kategori terpilih awal
        val courses: List<Course> = dummyCourses,
        val instructors: List<Instructor> = dummyInstructors,
        val currentBottomNavRoute: String = BottomNavItem.Home.route, // Layar aktif awal
        val isLoading: Boolean = false
        // Tambahkan state lain jika perlu
    )
}

// Implementasi default untuk komponen Home
class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val onLogout: () -> Unit, // Callback untuk aksi logout (navigasi)
    // Tambahkan callback lain jika perlu (misal, navigasi ke detail kursus)
    private val onNavigateToCourseDetail: (String) -> Unit,
    private val onNavigateToLogin: () -> Unit,
) : HomeComponent, ComponentContext by componentContext {

    private val _state = MutableValue(HomeComponent.State())
    override val state: Value<HomeComponent.State> = _state

    override fun onSearchQueryChanged(query: String) {
        // TODO: Implementasi logika pencarian atau update state
        _state.value = _state.value.copy(searchQuery = query)
    }

    override fun onCategorySelected(category: Category) {
        // TODO: Implementasi logika filter berdasarkan kategori
        _state.value = _state.value.copy(selectedCategory = category)
        println("Category selected: ${category.nameRes}")
    }

    override fun onCourseClicked(courseId: String) {
        // TODO: Implementasi navigasi ke detail kursus
        println("Course clicked: $courseId")
        onNavigateToCourseDetail(courseId) // Panggil callback navigasi
    }

    override fun onInstructorClicked(instructorId: String) {
        // TODO: Implementasi navigasi ke profil instruktur
        println("Instructor clicked: $instructorId")
    }

    override fun onBottomNavItemSelected(route: String) {
        // TODO: Implementasi navigasi antar tab utama atau update state
        _state.value = _state.value.copy(currentBottomNavRoute = route)
        println("Bottom nav selected: $route")
        // Jika menggunakan Decompose untuk navigasi tab, panggil fungsi navigasi di sini
    }

    override fun onLogoutClicked() {
        println("Logout initiated from HomeComponent.")
        onLogout() // Panggil callback logout
    }

    // init {
    //     // TODO: Muat data awal saat komponen dibuat
    //     loadInitialData()
    // }

    // private fun loadInitialData() {
    //     // Panggil repository/use case untuk memuat data
    //     // Update _state.value
    // }

    // Tambahkan lifecycle handling jika diperlukan
    // init {
    //     lifecycle.onDestroy {
    //         // Cleanup logic
    //     }
    // }
}
