package com.mnb.manobacademy.features.auth.ui // Atau package onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color // <<< Import Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mnb.manobacademy.ui.components.PrimaryActionButton // Ganti path jika perlu
import com.mnb.manobacademy.ui.theme.dimens
import com.mnb.manobacademy.getScreenHeightDp // <<< IMPORT FUNGSI EXPECT
import manobacademykmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.text.font.FontStyle // <<< Import FontStyle
import androidx.compose.ui.text.font.FontWeight // <<< Import FontWeight (Compose version)
import com.mnb.manobacademy.features.auth.store.onboardingPages
// Hapus import org.jetbrains.skia.FontWeight jika tidak digunakan di tempat lain

/**
 * Composable untuk layar panduan/onboarding awal aplikasi dengan 3 halaman swipeable.
 * Menampilkan tombol "Berikutnya" pada halaman 1 & 2, dan "Mulai" pada halaman terakhir.
 * Menyesuaikan padding atas pada layar compact yang tinggi.
 *
 * PENTING: Composable ini HARUS dipanggil dari dalam wrapper `AppTheme`.
 *
 * @param onGetStarted Lambda yang dipanggil saat tombol "Mulai" di halaman terakhir diklik.
 */
@OptIn(ExperimentalFoundationApi::class) // Untuk Pager
@Composable
fun GuideScreen(
    onGetStarted: () -> Unit
) {
    val dimens = MaterialTheme.dimens // Akses dimensi dari tema
    val pageCount = onboardingPages.size
    val pagerState = rememberPagerState { pageCount } // State untuk mengontrol Pager
    val scope = rememberCoroutineScope() // Scope untuk menjalankan coroutine (animasi scroll)

    // Dapatkan tinggi layar menggunakan fungsi expect
    val screenHeight = getScreenHeightDp()
    // Tentukan ambang batas tinggi untuk penyesuaian (sesuaikan nilai ini)
    val tallScreenThreshold = 700.dp
    // Hitung padding atas tambahan jika layar tinggi
    val extraTopPadding = if (screenHeight > tallScreenThreshold) 32.dp else 0.dp

    // Warna gradient untuk overlay di bagian bawah agar teks lebih mudah dibaca
    val gradientColorStart = Color.Transparent
    val gradientColorMid = MaterialTheme.colorScheme.background.copy(alpha = 0.75f)
    val gradientColorEnd = MaterialTheme.colorScheme.background.copy(alpha = 1.0f)

    val isDark = isSystemInDarkTheme() // Cek tema gelap/terang

    // Tentukan warna teks berdasarkan tema
    val textColor = if (isDark) Color.White else Color.DarkGray // Putih untuk Dark, Abu Gelap untuk Light

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent // Penting untuk edge-to-edge dengan gambar latar
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // --- Latar Belakang Pager (Gambar di dalam Pager) ---
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { pageIndex ->
                val pageData = onboardingPages[pageIndex]
                Image(
                    painter = painterResource(pageData.imageRes),
                    contentDescription = stringResource(pageData.imageContentDescRes),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // --- Overlay Gradient (Di atas Pager) ---
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(gradientColorStart, gradientColorStart, gradientColorMid, gradientColorEnd),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            // --- Konten Utama (Logo, Teks, Kontrol Bawah) ---
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding() // Padding otomatis untuk status bar
                    .navigationBarsPadding().padding(bottom = dimens.paddingHuge)// add more padding
                    .padding(horizontal = dimens.paddingHuge)// Padding kiri & kanan
                    .padding(bottom = dimens.paddingLarge), // Padding bawah eksplisit
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Spacer tambahan di atas logo untuk layar tinggi
                Spacer(modifier = Modifier.height(extraTopPadding)) // <<< GUNAKAN PADDING TAMBAHAN

                // Logo Aplikasi
                val logoResource: DrawableResource = if (isDark) {
                    Res.drawable.logo_manob_academy_dark // Ganti dengan resource logo mode gelap
                } else {
                    Res.drawable.logo_manob_academy_light // Ganti dengan resource logo mode terang
                }
                Image(
                    painter = painterResource(logoResource),
                    contentDescription = stringResource(Res.string.logo_content_description), // Ganti deskripsi
                    modifier = Modifier
                        .height(dimens.guideLogoSize) // Tinggi logo dari Dimens
                )

                // Spacer utama untuk mendorong konten ke bawah
                Spacer(modifier = Modifier.weight(1f))

                // --- Konten Teks (Headline dan Subtitle) ---
                Column(modifier = Modifier.fillMaxWidth()) {
                    val currentPageData = onboardingPages[pagerState.currentPage]
                    Text(
                        text = stringResource(currentPageData.headlineRes),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold, // <<< SUDAH BOLD (Bisa ganti ke FontWeight.Black jika perlu lebih tebal & font mendukung)
                        textAlign = TextAlign.Start,
                        color = textColor, // <<< WARNA DINAMIS BERDASARKAN TEMA
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(dimens.spacingSmall))
                    Text(
                        text = stringResource(currentPageData.subtitleRes),
                        style = MaterialTheme.typography.bodyLarge, // <<< STYLE LEBIH KECIL
                        fontStyle = FontStyle.Italic, // <<< STYLE ITALIC (GARIS MIRING)
                        textAlign = TextAlign.Start,
                        color = textColor, // <<< WARNA DINAMIS BERDASARKAN TEMA
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(dimens.guideTextPaddingBottom))

                // --- Kontrol Bawah (Indikator Halaman dan Tombol Aksi) ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PagerIndicator(
                        pageCount = pageCount,
                        currentPage = pagerState.currentPage
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    if (pagerState.currentPage == pageCount - 1) {
                        PrimaryActionButton(
                            text = stringResource(Res.string.guide_button_start),
                            onClick = onGetStarted,
                            modifier = Modifier.padding(start = dimens.spacingLarge)
                        )
                    } else {
                        PrimaryActionButton(
                            text = stringResource(Res.string.guide_button_next),
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            },
                            modifier = Modifier.padding(start = dimens.spacingLarge)
                        )
                    }
                } // Akhir Row Kontrol Bawah
            } // Akhir Column Konten Utama
        } // Akhir Box Latar Belakang + Overlay
    } // Akhir Surface
}


/**
 * Composable internal untuk menampilkan indikator halaman Pager (titik-titik).
 * (Tidak ada perubahan di PagerIndicator)
 *
 * @param pageCount Jumlah total halaman.
 * @param currentPage Indeks halaman yang sedang aktif.
 * @param modifier Modifier untuk kustomisasi layout.
 */
@Composable
private fun PagerIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    val dimens = MaterialTheme.dimens
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimens.guideIndicatorSpacing)
    ) {
        repeat(pageCount) { iteration ->
            val color = if (currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
            // Lebar indikator aktif dibuat lebih panjang
            val width = if (currentPage == iteration) dimens.guideIndicatorWidth else dimens.guideIndicatorHeight

            Box(
                modifier = Modifier
                    .width(width) // Lebar dinamis
                    .height(dimens.guideIndicatorHeight) // Tinggi tetap
                    .clip(CircleShape) // Bentuk bulat (atau RoundedCornerShape jika ingin kapsul)
                    .background(color)
            )
        }
    }
}