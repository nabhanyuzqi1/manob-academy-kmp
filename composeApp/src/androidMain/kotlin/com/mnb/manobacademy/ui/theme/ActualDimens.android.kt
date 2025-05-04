package com.mnb.manobacademy.ui.theme

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.mnb.manobacademy.ui.theme.CompactDimens
import com.mnb.manobacademy.ui.theme.ExpandedDimens
import com.mnb.manobacademy.ui.theme.LocalDimens
import com.mnb.manobacademy.ui.theme.MediumDimens

// Import Dimensions, LocalDimens, dll dari commonMain jika perlu

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
actual fun ProvidePlatformSpecificDimens(content: @Composable () -> Unit) {
    // Gunakan LocalActivity.current untuk mendapatkan Activity dengan aman
    val activity = LocalActivity.current

    // Tambahkan penanganan jika activity null (misalnya dalam preview Composable
    // atau konteks lain di mana Activity tidak tersedia)
    if (activity == null) {
        // Di preview atau kasus lain tanpa activity, provide default MediumDimens
        CompositionLocalProvider(LocalDimens provides MediumDimens) {
            content()
        }
        // Hapus print statement jika tidak diperlukan lagi untuk debugging
        // println("Window size class: Preview/Unknown")
        // println("Dimensions: Using Medium (Default)")
        // println("Local Dimens: ${LocalDimens.current}")
        return
    }

    // Hitung WindowSizeClass menggunakan activity yang didapat
    val windowSizeClass = calculateWindowSizeClass(activity)
    val dimensions = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> CompactDimens
        WindowWidthSizeClass.Medium -> MediumDimens
        WindowWidthSizeClass.Expanded -> ExpandedDimens
        else -> MediumDimens // Fallback ke Medium jika ada kasus lain
    }

    // Hapus print statement jika tidak diperlukan lagi untuk debugging
    println("Window size class: ${windowSizeClass.widthSizeClass}")
    println("Dimensions: $dimensions")


    // Sediakan (Provide) 'dimensions' yang terpilih ke CompositionLocal
    CompositionLocalProvider(LocalDimens provides dimensions) {
        // Hapus print statement jika tidak diperlukan lagi untuk debugging
        // println("Local Dimens (Provided): ${LocalDimens.current}")
        content() // Render konten aplikasi Anda di sini
    }
}
