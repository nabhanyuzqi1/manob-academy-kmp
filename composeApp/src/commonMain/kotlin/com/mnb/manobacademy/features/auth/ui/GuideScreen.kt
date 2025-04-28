package com.mnb.manobacademy.features.auth.ui // Atau package onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color // Import Color jika masih diperlukan untuk gradient
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp // Import dp jika perlu override dimens
// Import komponen & utilitas
import com.mnb.manobacademy.ui.components.PrimaryActionButton
import com.mnb.manobacademy.ui.theme.dimens
// Import Resources
import manobacademykmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Composable untuk layar panduan/onboarding awal aplikasi.
 * Dengan layout yang disesuaikan: background, teks rata kiri, tombol & indikator di bawah.
 *
 * PENTING: Composable ini HARUS dipanggil dari dalam wrapper `AppTheme`.
 *
 * @param onGetStarted Lambda yang dipanggil saat tombol "Mulai" diklik.
 */
@Composable
fun GuideScreen(
    onGetStarted: () -> Unit
) {
    val dimens = MaterialTheme.dimens
    val isDark = isSystemInDarkTheme()

    // Warna gradient untuk overlay (opsional, sesuaikan alpha jika gambar sudah gelap)
    val gradientColorStart = Color.Transparent // Mulai transparan di atas
    val gradientColorEnd = MaterialTheme.colorScheme.background.copy(alpha = 0.85f) // Lebih gelap di bawah

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background // Background fallback
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // --- Latar Belakang Gambar ---
            Image(
                painter = painterResource(Res.drawable.bg_guide_1), // <<< Gunakan gambar Anda
                contentDescription = stringResource(Res.string.guide_background_desc),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Crop agar mengisi layar
            )

            // --- Overlay Gradient (Opsional) ---
            // Memberi efek gelap di bagian bawah agar teks lebih kontras
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(gradientColorStart, gradientColorEnd),
                            startY = 0f, // Mulai dari atas
                            endY = Float.POSITIVE_INFINITY // Sampai bawah
                        )
                    )
            )

            // --- Konten Utama ---
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimens.paddingHuge) // Padding horizontal
                    .padding(bottom = dimens.paddingExtraLarge) // Padding bawah keseluruhan
            ) {
                Spacer(modifier = Modifier.height(dimens.topSpacingMobile)) // Jarak dari atas

                // Logo
                val logoResource: DrawableResource = if (isDark) {
                    Res.drawable.logo_manob_academy_light
                } else {
                    Res.drawable.logo_manob_academy_light
                }
                Image(
                    painter = painterResource(logoResource),
                    contentDescription = stringResource(Res.string.logo_content_description),
                    modifier = Modifier
                        .height(dimens.guideLogoSize) // Ukuran logo
                        .align(Alignment.CenterHorizontally) // Logo tetap di tengah horizontal
                )

                Spacer(modifier = Modifier.weight(1f)) // Dorong teks dan elemen bawah ke bawah

                // Teks Headline (Rata Kiri)
                Text(
                    text = stringResource(Res.string.guide_headline),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Start, // <<< Rata Kiri
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth() // Mengisi lebar agar rata kiri bekerja
                )
                Spacer(modifier = Modifier.height(dimens.spacingSmall))

                // Teks Subtitle (Rata Kiri)
                Text(
                    text = stringResource(Res.string.guide_subtitle),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Start, // <<< Rata Kiri
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth() // Mengisi lebar
                )

                Spacer(modifier = Modifier.height(dimens.guideTextPaddingBottom)) // Jarak ke elemen bawah

                // --- Baris Bawah (Indikator Kiri, Tombol Kanan) ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically // Pusatkan item secara vertikal
                ) {
                    // Indikator Halaman (Kiri)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(dimens.guideIndicatorSpacing)
                    ) {
                        // Indikator aktif
                        Box(
                            modifier = Modifier
                                .width(dimens.guideIndicatorWidth)
                                .height(dimens.guideIndicatorHeight)
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.primary)
                        )
                        // Indikator tidak aktif
                        Box(
                            modifier = Modifier
                                .width(dimens.guideIndicatorWidth)
                                .height(dimens.guideIndicatorHeight)
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                        // Tambahkan lebih banyak jika perlu
                    }

                    Spacer(modifier = Modifier.weight(1f)) // Dorong tombol ke kanan

                    // Tombol Mulai (Kanan, lebih kecil)
                    PrimaryActionButton(
                        text = stringResource(Res.string.guide_button_start),
                        onClick = onGetStarted,
                        // Hapus fillMaxWidth, atur padding atau width jika perlu
                        modifier = Modifier
                            // .width(150.dp) // Contoh: Atur lebar tetap jika perlu
                            .padding(start = dimens.spacingLarge) // Beri jarak dari spacer
                    )
                } // Akhir Row Bawah
            } // Akhir Column Konten Utama
        } // Akhir Box Latar Belakang + Overlay
    } // Akhir Surface
}
