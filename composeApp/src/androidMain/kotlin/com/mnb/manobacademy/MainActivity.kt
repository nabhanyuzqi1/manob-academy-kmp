package com.mnb.manobacademy // Sesuaikan package

import android.content.res.Configuration
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
import com.arkivanov.decompose.defaultComponentContext
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
import com.mnb.manobacademy.views.auth.ui.ForgotPasswordScreen
import com.mnb.manobacademy.views.auth.ui.GuideScreen
import com.mnb.manobacademy.views.auth.ui.LoginScreen
import com.mnb.manobacademy.views.auth.ui.RegistrationScreen
import com.mnb.manobacademy.views.auth.ui.SplashScreen
import com.mnb.manobacademy.views.auth.ui.VerificationCodeScreen
// Import Komponen (untuk dummy preview)
import com.mnb.manobacademy.views.auth.component.ForgotPasswordComponent
import com.mnb.manobacademy.views.auth.component.LoginComponent
import com.mnb.manobacademy.views.auth.component.ResetMethod

// --- PERBAIKAN IMPORT ---
import com.mnb.manobacademy.navigation.DefaultRootComponent

import com.mnb.manobacademy.ui.theme.AppDimens
// Import Tema
import com.mnb.manobacademy.ui.theme.AppTheme
import com.mnb.manobacademy.views.auth.component.VerificationCodeComponent
import com.mnb.manobacademy.models.VerificationState
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
            setKeepOnScreenCondition { true }
            lifecycleScope.launch {
                delay(2000)
                setKeepOnScreenCondition { false }
            }
            super.onCreate(savedInstanceState)
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
                navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
            )
        }

        val root = DefaultRootComponent(defaultComponentContext())

        WindowCompat.setDecorFitsSystemWindows(window, false)
        try {
            setContent {
                App(root = root)
            }
        } catch (e: Throwable) {
            Log.e("MainActivity", "Fatal exception in setContent", e)
            finish()
        }
    }
}


// --- Bagian Preview ---

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AppAndroidPreview() {
    AppTheme {
        LoginScreenPreview()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    AppTheme {
        val dummyLoginComponent = object : LoginComponent {
            override val state: Value<LoginComponent.State> =
                MutableValue(LoginComponent.State())
            override fun onEmailChanged(text: String) {}
            override fun onPasswordChanged(text: String) {}
            override fun onLoginClicked() {}
            override fun onRegisterClicked() {}
            override fun onForgotPasswordClicked() {}
        }
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

// --- PERBAIKAN PREVIEW DI SINI ---
@Preview(showSystemUi = true, showBackground = true, name = "Verification Screen")
@Composable
fun VerificationCodeScreenPreview() {
    AppTheme {
        val dummyComponent = object : VerificationCodeComponent {
            override val state: Value<VerificationState> =
                MutableValue(
                    VerificationState(
                        isLoading = false,
                        error = null,
                        isResendEnabled = true,
                        resendCooldown = 0
                    )
                )
            override val emailAddress: String? = "preview@example.com"
            override fun onVerifyClicked(code: String) {}
            override fun onResendClicked() {}
            override fun onBackClicked() {}
            override fun onErrorDismissed() {}
        }
        // Panggilan menjadi sangat sederhana
        VerificationCodeScreen(
            component = dummyComponent
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GuideScreenPreview() {
    AppTheme {
        GuideScreen(
            onGetStarted = {}
        )
    }
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        val dummyHomeComponent = object : HomeComponent {
            override val state: Value<HomeComponent.State> =
                MutableValue(HomeComponent.State(
                    userName = "Nama Pengguna Preview",
                    favoriteCourse = FavoriteCourse("fav_prev", "Judul Kelas Favorit Preview", "Instruktur Preview", 4.5f, 10, 20, ""),
                    categories = listOf(
                        Category(Res.string.home_category_art, Icons.Default.Palette),
                        Category(Res.string.home_category_photography, Icons.Default.PhotoCamera),
                        Category(Res.string.home_category_design, Icons.Default.Draw)
                    ),
                    selectedCategory = Category(Res.string.home_category_art, Icons.Default.Palette),
                    courses = listOf(
                        Course("c_prev1", "Judul Kelas Preview 1", "Kategori A", 4.2f, "Rp 100.000", "Rp 50.000", ""),
                        Course("c_prev2", "Judul Kelas Preview 2", "Kategori B", 4.9f, "Rp 150.000", "Rp 75.000", "")
                    ),
                    instructors = listOf(
                        Instructor("i_prev1", "Instruktur Satu Preview", ""),
                        Instructor("i_prev2", "Instruktur Dua Preview", "")
                    ),
                    newsItems = dummyNewsItems,
                    currentBottomNavRoute = BottomNavItem.Home.route
                ))

            override fun onSearchQueryChanged(query: String) {}
            override fun onCategorySelected(category: Category) {}
            override fun onCourseClicked(courseId: String) {}
            override fun onInstructorClicked(instructorId: String) {}
            override fun onBottomNavItemSelected(route: String) {}
            override fun onLogoutClicked() {}
            override fun onNotificationClicked() {}
            override fun onViewAllClassesClicked() {}
            override fun onViewAllInstructorsClicked() {}
            override fun onViewAllNewsClicked() {}
            override fun onNewsItemClicked(newsId: String) {}
        }
        HomeScreen(component = dummyHomeComponent)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    AppTheme {
        EditProfileScreen()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SettingScreenPreview() {
    AppTheme {
        SettingsScreen(
            onNavigateBack = {}
        )
    }
}

class PreviewBookingComponent : BookingComponent {
    override val state: Value<BookingComponent.State> =
        MutableValue(
            BookingComponent.State(
                bookingItems = getDummyBookingItems(),
                subtotal = getDummyBookingItems().filter { it.isSelected }.sumOf { it.price },
                currentStep = 0,
                isFavorite = false
            )
        )
    override fun onBackClicked() {}
    override fun onFavoriteClicked() {}
    override fun onItemCheckedChanged(itemId: String, isChecked: Boolean) {}
    override fun onCheckoutClicked() {}
    override fun onBottomNavItemSelected(newRoute: String) {}
}

@Preview(showSystemUi = true, showBackground = true, name = "Booking Screen Light")
@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Booking Screen Dark")
@Composable
fun BookingScreenPreview() {
    AppTheme {
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
            dimens = AppDimens
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
                    it.copy(schedule = "01.08.2025")
                },
                totalAmount = getDummyBookingItems().take(2).sumOf { it.price }
            )
        )
    override fun onBackClicked() {}
    override fun onNavigateToPaymentMethod() {}
}

@Preview(showBackground = true, showSystemUi = true, name = "Checkout Screen Light")
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Checkout Screen Dark")
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
            selectedPaymentMethodId = getDummyPaymentMethods().first { it.category == PaymentCategory.E_MONEY }.id,
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
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Payment Screen Dark")
@Composable
fun PaymentScreenPreview() {
    AppTheme {
        PaymentScreen(component = PreviewPaymentComponent())
    }
}