// --- File: composeApp/src/commonMain/kotlin/com/mnb/manobacademy/ScreenVisibility.kt ---
package com.mnb.manobacademy.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.mnb.manobacademy.navigation.RootComponent

expect fun shouldShowSplash(root: RootComponent,onNavigateToLogin: () -> Unit) : @Composable () -> Unit

@Composable
internal expect fun getScreenHeightDp(): Dp