package com.mnb.manobacademy.views.home.component // Ensure this package is correct

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.mnb.manobacademy.models.BottomNavItem
import com.mnb.manobacademy.models.Category
import com.mnb.manobacademy.models.Course
import com.mnb.manobacademy.models.FavoriteCourse
import com.mnb.manobacademy.models.Instructor
import com.mnb.manobacademy.models.NewsItem
// Assuming dummy data is in models or a shared location accessible here
import com.mnb.manobacademy.models.dummyCategories
import com.mnb.manobacademy.models.dummyCourses
import com.mnb.manobacademy.models.dummyFavorite
import com.mnb.manobacademy.models.dummyInstructors
import com.mnb.manobacademy.models.dummyNewsItems


// Interface untuk komponen Home
interface HomeComponent {
    val state: Value<State> // State UI

    // Existing methods
    fun onSearchQueryChanged(query: String)
    fun onCategorySelected(category: Category)
    fun onCourseClicked(courseId: String)
    fun onInstructorClicked(instructorId: String)
    fun onBottomNavItemSelected(route: String)
    fun onLogoutClicked()

    // New methods that were missing implementation in your Activity/Component Host
    fun onNotificationClicked()
    fun onViewAllClassesClicked()
    fun onViewAllInstructorsClicked()
    fun onViewAllNewsClicked()
    fun onNewsItemClicked(newsId: String)

    // State untuk HomeScreen
    data class State(
        val userName: String = "Dryx Siregar", // Ambil dari data user nanti
        val searchQuery: String = "",
        val favoriteCourse: FavoriteCourse? = dummyFavorite, // Ganti dengan data asli
        val categories: List<Category> = dummyCategories,
        val selectedCategory: Category? = dummyCategories.firstOrNull(), // Kategori terpilih awal
        val courses: List<Course> = dummyCourses,
        val instructors: List<Instructor> = dummyInstructors,
        val newsItems: List<NewsItem> = dummyNewsItems, // Added newsItems to state
        val currentBottomNavRoute: String = BottomNavItem.Home.route, // Layar aktif awal
        val isLoading: Boolean = false
        // Tambahkan state lain jika perlu
    )
}

// Implementasi default untuk komponen Home
class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val onLogout: () -> Unit,
    private val onNavigateToCourseDetail: (String) -> Unit,
    private val onNavigateToLogin: () -> Unit, // Assuming this is used somewhere or for future use
    // Add other navigation callbacks as needed
    private val onNavigateToNotifications: () -> Unit,
    private val onNavigateToAllClasses: () -> Unit,
    private val onNavigateToAllInstructors: () -> Unit,
    private val onNavigateToAllNews: () -> Unit,
    private val onNavigateToNewsDetail: (String) -> Unit,

    ) : HomeComponent, ComponentContext by componentContext {

    private val _state = MutableValue(HomeComponent.State())
    override val state: Value<HomeComponent.State> = _state

    override fun onSearchQueryChanged(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        println("Search query changed: $query")
    }

    override fun onCategorySelected(category: Category) {
        _state.value = _state.value.copy(selectedCategory = category)
        // TODO: Implementasi logika filter berdasarkan kategori, e.g., reload courses
        println("Category selected: ${category.nameRes}")
    }

    override fun onCourseClicked(courseId: String) {
        println("Course clicked: $courseId")
        onNavigateToCourseDetail(courseId)
    }

    override fun onInstructorClicked(instructorId: String) {
        // TODO: Implementasi navigasi ke profil instruktur
        println("Instructor clicked: $instructorId")
        // Example: onNavigateToInstructorProfile(instructorId)
    }

    override fun onBottomNavItemSelected(route: String) {
        _state.value = _state.value.copy(currentBottomNavRoute = route)
        println("Bottom nav selected: $route")
        // TODO: Handle actual navigation if using Decompose navigation for tabs
    }

    override fun onLogoutClicked() {
        println("Logout initiated from HomeComponent.")
        onLogout()
    }

    // Implementations for the new methods
    override fun onNotificationClicked() {
        println("Notification icon clicked")
        onNavigateToNotifications()
        // TODO: Implement navigation to notifications screen or show a dialog/dropdown
    }

    override fun onViewAllClassesClicked() {
        println("View All Classes clicked")
        onNavigateToAllClasses()
        // TODO: Implement navigation to a screen showing all classes
    }

    override fun onViewAllInstructorsClicked() {
        println("View All Instructors clicked")
        onNavigateToAllInstructors()
        // TODO: Implement navigation to a screen showing all instructors
    }

    override fun onViewAllNewsClicked() {
        println("View All News clicked")
        onNavigateToAllNews()
        // TODO: Implement navigation to a screen showing all news
    }

    override fun onNewsItemClicked(newsId: String) {
        println("News item clicked: $newsId")
        onNavigateToNewsDetail(newsId)
        // TODO: Implement navigation to the detail screen for the news item
    }

    // init {
    //     // TODO: Muat data awal saat komponen dibuat (e.g., fetch from a repository)
    //     // loadInitialData()
    // }

    // private fun loadInitialData() {
    //     _state.value = _state.value.copy(isLoading = true)
    //     // viewModelScope.launch {
    //     //     try {
    //     //         val fetchedUserName = "User from API" // userRepository.getUserName()
    //     //         val fetchedFavoriteCourse = null // courseRepository.getFavoriteCourse()
    //     //         val fetchedCategories = emptyList<Category>() // categoryRepository.getCategories()
    //     //         val fetchedCourses = emptyList<Course>() // courseRepository.getCourses(selectedCategory = _state.value.selectedCategory)
    //     //         val fetchedInstructors = emptyList<Instructor>() // instructorRepository.getInstructors()
    //     //         val fetchedNews = emptyList<NewsItem>() // newsRepository.getNews()

    //     //         _state.value = _state.value.copy(
    //     //             userName = fetchedUserName,
    //     //             favoriteCourse = fetchedFavoriteCourse,
    //     //             categories = fetchedCategories.ifEmpty { dummyCategories },
    //     //             selectedCategory = fetchedCategories.firstOrNull() ?: dummyCategories.firstOrNull(),
    //     //             courses = fetchedCourses.ifEmpty { dummyCourses },
    //     //             instructors = fetchedInstructors.ifEmpty { dummyInstructors },
    //     //             newsItems = fetchedNews.ifEmpty { dummyNewsItems },
    //     //             isLoading = false
    //     //         )
    //     //     } catch (e: Exception) {
    //     //         _state.value = _state.value.copy(isLoading = false)
    //     //         // Handle error, e.g., show a message
    //     //         println("Error loading initial data: ${e.message}")
    //     //     }
    //     // }
    // }
}
