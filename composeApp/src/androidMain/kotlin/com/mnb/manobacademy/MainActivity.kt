package com.mnb.manobacademy // Sesuaikan package

import android.graphics.Color
import android.util.Log
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.arkivanov.decompose.defaultComponentContext // <<< Pastikan ini diimpor
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.mnb.manobacademy.models.BookingItem
import com.mnb.manobacademy.views.home.ui.HomeScreen
import com.mnb.manobacademy.models.BottomNavItem
import com.mnb.manobacademy.models.Category
import com.mnb.manobacademy.models.Course
import com.mnb.manobacademy.models.FavoriteCourse
import com.mnb.manobacademy.models.Instructor
import com.mnb.manobacademy.models.PaymentCategory
import com.mnb.manobacademy.models.dummyNewsItems
import com.mnb.manobacademy.models.getDummyBookingItems
import com.mnb.manobacademy.models.getDummyPaymentMethods
// Import UI Screens
import com.mnb.manobacademy.views.auth.ui.ForgotPasswordScreen // Import ForgotPasswordScreen
import com.mnb.manobacademy.views.auth.ui.GuideScreen // <<< IMPORT GuideScreen >>>
import com.mnb.manobacademy.views.auth.ui.LoginScreen
import com.mnb.manobacademy.views.auth.ui.RegistrationScreen
import com.mnb.manobacademy.views.auth.ui.SplashScreen
import com.mnb.manobacademy.views.auth.ui.VerificationCodeScreen
// Import Komponen (untuk dummy preview)
import com.mnb.manobacademy.views.auth.component.ForgotPasswordComponent
import com.mnb.manobacademy.views.auth.component.LoginComponent
import com.mnb.manobacademy.views.auth.component.ResetMethod

import com.mnb.manobacademy.navigation.RootComponent.DefaultRootComponent
import com.mnb.manobacademy.ui.theme.AppDimens
// import com.mnb.manobacademy.navigation.RootContent // Tidak perlu diimport jika hanya memanggil App()
// Import Tema
import com.mnb.manobacademy.ui.theme.AppTheme // <- Import AppTheme Anda
import com.mnb.manobacademy.views.home.component.HomeComponent
import com.mnb.manobacademy.views.booking.component.BookingComponent
import com.mnb.manobacademy.views.booking.ui.BookingListItem
import com.mnb.manobacademy.views.booking.ui.BookingScreen
import com.mnb.manobacademy.views.checkout.component.CheckoutComponent
import com.mnb.manobacademy.views.checkout.ui.CheckoutScreen
import com.mnb.manobacademy.views.payment.component.PaymentComponent
import com.mnb.manobacademy.views.payment.ui.PaymentScreen
import com.mnb.manobacademy.views.profile.ui.EditProfileScreen
import com.mnb.manobacademy.views.profile.ui.ProfileScreen
import com.mnb.manobacademy.views.settings.ui.SettingsScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import manobacademykmp.composeapp.generated.resources.Res
import manobacademykmp.composeapp.generated.resources.home_category_art
import manobacademykmp.composeapp.generated.resources.home_category_design
import manobacademykmp.composeapp.generated.resources.home_category_photography


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            // Tambahkan kode berikut untuk mengatur durasi splash screen
            setKeepOnScreenCondition { true }
            lifecycleScope.launch {
                delay(2000)
                setKeepOnScreenCondition { false }
            }
            super.onCreate(savedInstanceState)
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
                navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT) // light causes internally enforce the navigation bar to be fully transparent
            )
        }
        // Gunakan defaultComponentContext() dari Decompose untuk membuat ComponentContext dasar
        // Pastikan constructor DefaultRootComponent menerima ComponentContext
        val root = DefaultRootComponent(defaultComponentContext()) // <<< Panggil defaultComponentContext()

        WindowCompat.setDecorFitsSystemWindows(window, false) // <<< Add this line
        try {
            setContent {
                App(root = root)
            }
        } catch (e: Throwable) {
            // Tangani semua jenis pengecualian yang mungkin terjadi
            Log.e("MainActivity", "Fatal exception in setContent", e)
            // Tampilkan pesan error ke user atau lakukan fallback lain
            // Contoh:
            // Toast.makeText(this, "A fatal error occurred", Toast.LENGTH_LONG).show()
            // atau
            // showDialog("A fatal error occurred", "Please restart the app or contact support.")
            // Akhirnya, exit() aplikasinya
            finish()
        }
    }
}


// --- Bagian Preview ---

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AppAndroidPreview() {
    AppTheme {
        LoginScreenPreview() // Tampilkan salah satu layar sebagai contoh
    }
}

// --- Preview untuk LoginScreen yang Diperbaiki ---
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    AppTheme {
        // Buat dummy LoginComponent untuk preview
        val dummyLoginComponent = object : LoginComponent {
            override val state: Value<LoginComponent.State> =
                MutableValue(LoginComponent.State()) // State awal default

            override fun onEmailChanged(text: String) {}
            override fun onPasswordChanged(text: String) {}
            override fun onLoginClicked() {}
            override fun onRegisterClicked() {}
            override fun onForgotPasswordClicked() {}
        }
        // Panggil LoginScreen dengan dummy component
        LoginScreen(component = dummyLoginComponent)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    AppTheme {
        RegistrationScreen(
            onNavigateToLogin = {},
            onRegisterSuccess = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SplashScreenPreview() {
    AppTheme {
        SplashScreen (
            onNavigateToLogin = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun VerificationCodeScreenPreview() {
    AppTheme {
        VerificationCodeScreen (
            onNavigateBack = {},
            onVerifyClick = {},
            onResendClick = {},
            emailAddress = "preview@example.com"
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    AppTheme {
        val dummyComponent = object : ForgotPasswordComponent {
            override val state: Value<ForgotPasswordComponent.State> =
                MutableValue(ForgotPasswordComponent.State(
                    maskedPhoneNumber = "+62 812-xxxx-5678",
                    maskedEmail = "preview-xxx@example.com",
                    selectedMethod = ResetMethod.NONE,
                    isResetEnabled = false
                ))
            override fun onMethodSelected(method: ResetMethod) {}
            override fun onResetClicked() {}
            override fun onBackClicked() {}
        }
        ForgotPasswordScreen(component = dummyComponent)
    }
}

// --- Preview untuk GuideScreen ---
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GuideScreenPreview() { // <<< Nama fungsi preview baru
    AppTheme { // <<< Bungkus dengan AppTheme
        GuideScreen(
            onGetStarted = {} // <<< Berikan lambda kosong
        )
    }
}

// --- Preview untuk HomeScreen ---
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme { // Ensure AppTheme provides MaterialTheme and Dimens
        // Buat dummy HomeComponent
        val dummyHomeComponent = object : HomeComponent {
            // Sediakan state default untuk preview
            override val state: Value<HomeComponent.State> =
                MutableValue(HomeComponent.State(
                    userName = "Nama Pengguna Preview",
                    favoriteCourse = FavoriteCourse("fav_prev", "Judul Kelas Favorit Preview", "Instruktur Preview", 4.5f, 10, 20, ""),
                    categories = listOf(
                        Category(Res.string.home_category_art, Icons.Default.Palette),
                        Category(Res.string.home_category_photography, Icons.Default.PhotoCamera),
                        Category(Res.string.home_category_design, Icons.Default.Draw)
                    ),
                    selectedCategory = Category(Res.string.home_category_art, Icons.Default.Palette), // Example selected
                    courses = listOf(
                        Course("c_prev1", "Judul Kelas Preview 1", "Kategori A", 4.2f, "Rp 100.000", "Rp 50.000", ""),
                        Course("c_prev2", "Judul Kelas Preview 2", "Kategori B", 4.9f, "Rp 150.000", "Rp 75.000", "")
                    ),
                    instructors = listOf(
                        Instructor("i_prev1", "Instruktur Satu Preview", ""),
                        Instructor("i_prev2", "Instruktur Dua Preview", "")
                    ),
                    newsItems = dummyNewsItems, // Added dummy news items for preview
                    currentBottomNavRoute = BottomNavItem.Home.route
                ))

            // Implementasi fungsi kosong untuk preview
            override fun onSearchQueryChanged(query: String) { println("Preview: Search changed to $query") }
            override fun onCategorySelected(category: Category) { println("Preview: Category selected: ${category.nameRes}") }
            override fun onCourseClicked(courseId: String) { println("Preview: Course clicked: $courseId") }
            override fun onInstructorClicked(instructorId: String) { println("Preview: Instructor clicked: $instructorId") }
            override fun onBottomNavItemSelected(route: String) { println("Preview: Nav item selected: $route") }
            override fun onLogoutClicked() { println("Preview: Logout clicked") }
            // Added missing methods for preview
            override fun onNotificationClicked() { println("Preview: Notification clicked") }
            override fun onViewAllClassesClicked() { println("Preview: View All Classes clicked") }
            override fun onViewAllInstructorsClicked() { println("Preview: View All Instructors clicked") }
            override fun onViewAllNewsClicked() { println("Preview: View All News clicked") }
            override fun onNewsItemClicked(newsId: String) { println("Preview: News item clicked: $newsId") }
        }
        // Panggil HomeScreen dengan dummy component
        HomeScreen(component = dummyHomeComponent)
    }
}

// --- Preview untuk ProfileScreen ---
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen()
    }
}

// --- Preview untuk EditProfileScreen ---
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    AppTheme {
        EditProfileScreen()
    }
}

// --- Preview untuk SettingScreen ---
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SettingScreenPreview() {
    AppTheme {
        SettingsScreen(
            onNavigateBack = {}
        )
    }
}

// --- Preview Booking Component (Stub Implementation) ---
class PreviewBookingComponent : BookingComponent {
    override val state: Value<BookingComponent.State> =
        MutableValue(
            BookingComponent.State(
                bookingItems = getDummyBookingItems(), // Gunakan dummy data
                subtotal = getDummyBookingItems().filter { it.isSelected }.sumOf { it.price },
                currentStep = 0, // Mulai dari step Checkout
                isFavorite = false // Contoh state favorit
            )
        )

    override fun onBackClicked() {
        println("Preview: Back clicked")
    }

    override fun onFavoriteClicked() {
        println("Preview: Favorite clicked")
        // Untuk interaktivitas di preview, Anda bisa update state di sini jika MutableValue dipegang oleh kelas ini
        // (state as? MutableValue)?.update { it.copy(isFavorite = !it.isFavorite) }
    }

    override fun onItemCheckedChanged(itemId: String, isChecked: Boolean) {
        println("Preview: Item $itemId changed to $isChecked")
        // (state as? MutableValue)?.update { currentState ->
        //     val updatedItems = currentState.bookingItems.map { item ->
        //         if (item.id == itemId) item.copy(isSelected = isChecked) else item
        //     }
        //     val newSubtotal = updatedItems.filter { it.isSelected }.sumOf { it.price }
        //     currentState.copy(bookingItems = updatedItems, subtotal = newSubtotal)
        // }
    }

    override fun onCheckoutClicked() {
        println("Preview: Checkout clicked")
    }

    override fun onBottomNavItemSelected(newRoute: String) {
        println("Preview: Bottom nav item selected: $newRoute")
        // (state as? MutableValue)?.update { it.copy(currentBottomNavRoute = newRoute) }
    }
}

// --- Preview untuk BookingScreen ---
@Preview(showSystemUi = true, showBackground = true, name = "Booking Screen Light")
@Preview(showSystemUi = true, showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, name = "Booking Screen Dark")
@Composable
fun BookingScreenPreview() {
    AppTheme { // Pastikan AppTheme Anda mengatur LocalDimens dan LocalColorScheme
        BookingScreen(
            component = PreviewBookingComponent()
        )
    }
}

@Preview(showBackground = true, name = "Booking Item Selected")
@Composable
fun BookingListItemSelectedPreview() {
    AppTheme {
        BookingListItem(
            item = BookingItem("prev1", null, "Fotografi Advanced", "Selasa, 21 April 2025", 150000.0, "Rp.150.000", true),
            onCheckedChanged = {},
            dimens = AppDimens // Atau dimens spesifik jika perlu diisolasi
        )
    }
}

@Preview(showBackground = true, name = "Booking Item Unselected")
@Composable
fun BookingListItemUnselectedPreview() {
    AppTheme {
        BookingListItem(
            item = BookingItem("prev2", null, "Workshop Editing", "Rabu, 22 April 2025", 120000.0, "Rp.120.000", false),
            onCheckedChanged = {},
            dimens = AppDimens
        )
    }
}

class PreviewCheckoutComponent : CheckoutComponent {
    override val state: Value<CheckoutComponent.State> =
        MutableValue(
            CheckoutComponent.State(
                itemsToCheckout = getDummyBookingItems().take(2).map {
                    // Modifikasi dummy data jika perlu, misal tanggal akhir
                    it.copy(schedule = "01.08.2025")
                },
                totalAmount = getDummyBookingItems().take(2).sumOf { it.price }
            )
        )
    override fun onBackClicked() {}
    override fun onNavigateToPaymentMethod() {}
}

@Preview(showBackground = true, showSystemUi = true, name = "Checkout Screen Light")
@Preview(showBackground = true, showSystemUi = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, name = "Checkout Screen Dark")
@Composable
fun CheckoutScreenPreview() {
    AppTheme {
        CheckoutScreen(component = PreviewCheckoutComponent())
    }
}

class PreviewPaymentComponent : PaymentComponent {
    private val _state = MutableValue(
        PaymentComponent.State(
            itemsToPay = getDummyBookingItems().take(1),
            totalAmount = getDummyBookingItems().first().price,
            paymentMethods = getDummyPaymentMethods(),
            selectedPaymentMethodId = getDummyPaymentMethods().first { it.category == PaymentCategory.E_MONEY }.id, // Pilih OVO sebagai default
            currentStep = 1
        )
    )
    override val state: Value<PaymentComponent.State> = _state

    override fun onBackClicked() {}
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
    override fun onPayNowClicked() {}
}

@Preview(showBackground = true, showSystemUi = true, name = "Payment Screen Light")
@Preview(showBackground = true, showSystemUi = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, name = "Payment Screen Dark")
@Composable
fun PaymentScreenPreview() {
    AppTheme {
        PaymentScreen(component = PreviewPaymentComponent())
    }
}