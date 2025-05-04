package com.mnb.manobacademy

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mnb.manobacademy.navigation.RootComponent

/**
 * Implementasi Android `actual` untuk `expect fun shouldShowSplash`.
 * Karena splash screen native Android (`installSplashScreen`) digunakan,
 * fungsi ini tidak perlu menampilkan UI Compose untuk splash.
 * Ia hanya perlu segera memanggil lambda navigasi agar alur berlanjut
 * setelah splash screen native selesai.
 *
 * @param root RootComponent (mungkin tidak diperlukan di implementasi ini).
 * @param onNavigateToLogin Lambda yang berisi logika navigasi setelah splash (cek first launch, dll.).
 * @return Composable kosong yang segera memanggil onNavigateToLogin.
 */
// <<< HAPUS @Composable DARI FUNGSI INI >>>
actual fun shouldShowSplash(root: RootComponent, onNavigateToLogin: () -> Unit): @Composable () -> Unit { // <<< @Composable HANYA PADA RETURN TYPE
    // Langsung panggil lambda navigasi karena splash native sudah/sedang berjalan.
    // Tidak perlu menampilkan Composable splash di sini.
    onNavigateToLogin() // <<< Gunakan nama parameter yang benar

    // Kembalikan Composable kosong karena kita tidak ingin menampilkan apa pun
    // dari sini. Navigasi sudah ditangani oleh pemanggilan onNavigateToLogin().
    return {}
}

@Composable
internal actual fun getScreenHeightDp(): Dp {
    return LocalConfiguration.current.screenHeightDp.dp
}