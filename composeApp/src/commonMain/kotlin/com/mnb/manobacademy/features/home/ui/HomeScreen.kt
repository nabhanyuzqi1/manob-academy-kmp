// --- File: composeApp/src/commonMain/kotlin/features/home/ui/HomeScreen.kt ---
// ... (Kode HomeScreen bisa tetap sebagai placeholder) ...
package com.mnb.manobacademy.features.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mnb.manobacademy.features.home.component.HomeComponent

@Composable
fun HomeScreen(component: HomeComponent) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Selamat Datang! (Home Screen)")
    }
}

