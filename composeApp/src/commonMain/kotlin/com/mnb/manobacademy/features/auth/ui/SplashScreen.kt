package com.mnb.manobacademy.features.auth.ui // Sesuaikan dengan package Anda

// Import Compose & Material Lengkap
// import androidx.compose.desktop.ui.tooling.preview.Preview // Preview mungkin tidak relevan di KMP non-Android
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// import androidx.compose.ui.unit.dp // Hapus jika tidak perlu
import kotlinx.coroutines.delay
// Import Tema & Dimens
import com.mnb.manobacademy.ui.theme.dimens // <- Import helper dimens
// Import Resources
import manobacademykmp.composeapp.generated.resources.* // <- Import semua resource
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource // <- Import stringResource

@OptIn(ExperimentalResourceApi::class)
// @Preview // Komentari atau hapus jika tidak menargetkan pratinjau Android secara spesifik
@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit
){
    // Waktu delay splash screen (bisa juga dimasukkan ke Dimens atau konstanta)
    val splashScreenDurationMillis = 3000L

    LaunchedEffect (Unit){
        delay(splashScreenDurationMillis)
        onNavigateToLogin()
    }

    // Akses dimensi dari tema
    val dimens = MaterialTheme.dimens

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ){
                // --- Logika Pemilihan Logo ---
                val isDarkTheme = isSystemInDarkTheme()
                val logoResource: DrawableResource = if (isDarkTheme) {
                    Res.drawable.logo_manob_academy_dark
                } else {
                    Res.drawable.logo_manob_academy_light
                }
                // -----------------------------

                Image(
                    painter = painterResource(logoResource),
                    contentDescription = stringResource(Res.string.logo_content_description), // <<< Gunakan string resource
                    modifier = Modifier.size(dimens.splashLogoSize) // <<< Gunakan dimensi tema
                )
            }
        }
    }
}
