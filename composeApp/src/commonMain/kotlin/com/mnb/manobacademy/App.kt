// --- File: composeApp/src/commonMain/kotlin/App.kt ---
package com.mnb.manobacademy

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mnb.manobacademy.navigation.RootComponent
import com.mnb.manobacademy.navigation.RootContent

// Inisialisasi Napier jika masih digunakan
// import io.github.aakira.napier.DebugAntilog
// Napier.base(DebugAntilog())

@Composable
fun App(root: RootComponent) {
    MaterialTheme {
        RootContent(component = root, modifier = Modifier.fillMaxSize())
    }
}
