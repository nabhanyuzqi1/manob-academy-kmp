// --- File: composeApp/src/commonMain/kotlin/navigation/RootComponent.kt ---
package com.mnb.manobacademy.navigation

// Import komponen Home jika masih ada
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.StackNavigator
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.mnb.manobacademy.features.home.component.DefaultHomeComponent
import com.mnb.manobacademy.features.home.component.HomeComponent


// Interface Root Component (Disederhanakan)
interface RootComponent {
    val stack: Value<ChildStack<*, Child>>
    val navigation: StackNavigator<ScreenConfig>

    // Child sekarang tidak perlu membawa state/logic component
    sealed interface Child {
        data object Splash : Child
        data object Login : Child
        data object Register : Child
        data class Home(val component: HomeComponent) : Child // Home mungkin masih perlu componentnya
    }
}

// Implementasi Root Component (Disederhanakan)
class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    override val navigation = StackNavigation<ScreenConfig>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = null, // Eksplisit null karena ScreenConfig tidak Parcelable/Serializable
            initialConfiguration = ScreenConfig.Splash, // Mulai dari Splash
            handleBackButton = true,
            childFactory = ::createChild
        )

    // childFactory sekarang lebih sederhana
    private fun createChild(config: ScreenConfig, context: ComponentContext): RootComponent.Child =
        when (config) {
            is ScreenConfig.Splash -> RootComponent.Child.Splash
            is ScreenConfig.Login -> RootComponent.Child.Login
            is ScreenConfig.Register -> RootComponent.Child.Register
            is ScreenConfig.Home -> RootComponent.Child.Home(
                // Anda masih bisa membuat HomeComponent jika diperlukan untuk layar Home
                DefaultHomeComponent(context)
            )
        }
}
