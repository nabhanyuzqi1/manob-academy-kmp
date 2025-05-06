package com.mnb.manobacademy.views.auth.component // <- Sesuaikan dengan package komponen Anda

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
// import androidx.compose.ui.graphics.Color // Hapus jika tidak ada lagi warna hardcoded
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import manobacademykmp.composeapp.generated.resources.Res // <- Sesuaikan path resource
import manobacademykmp.composeapp.generated.resources.ic_facebook // <- Sesuaikan path resource
import manobacademykmp.composeapp.generated.resources.ic_google // <- Sesuaikan path resource
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

// --- Komponen Tombol Individual (Lebih Fleksibel) ---

/**
 * Composable untuk sebuah tombol social login individual.
 * Menggunakan warna dari MaterialTheme.
 * PENTING: Harus digunakan di dalam lingkup `AppTheme`.
 *
 * @param onClick Aksi yang dijalankan saat tombol diklik.
 * @param iconRes Resource drawable untuk ikon social media.
 * @param contentDescription Deskripsi konten untuk aksesibilitas ikon.
 * @param modifier Modifier untuk diterapkan pada tombol.
 * @param buttonSize Ukuran (lebar dan tinggi) tombol. Default 50.dp.
 * @param iconSize Ukuran ikon di dalam tombol. Default 24.dp.
 * @param shape Bentuk tombol. Default RoundedCornerShape(20).
 * @param containerColor Warna latar belakang tombol. Default menggunakan `surfaceVariant` dari tema.
 * @param iconTint Warna tint untuk ikon. Default menggunakan `onSurfaceVariant` dari tema.
 */
@Composable
fun SocialLoginButton(
    onClick: () -> Unit,
    iconRes: DrawableResource,
    contentDescription: String,
    modifier: Modifier = Modifier,
    buttonSize: Dp = 50.dp,
    iconSize: Dp = 24.dp,
    shape: Shape = RoundedCornerShape(20),
    // Gunakan warna dari tema sebagai default
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    Button(
        onClick = onClick,
        shape = shape,
        // Gunakan containerColor yang diberikan (default dari tema)
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        modifier = modifier.size(buttonSize) // Gunakan ukuran dari parameter
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.requiredSize(iconSize), // Gunakan ukuran ikon dari parameter
            // Terapkan tint dari tema ke ikon
            //colorFilter = ColorFilter.tint(iconTint)
        )
    }
}


// --- Komponen Baris Tombol Google & Facebook (Convenience) ---

/**
 * Composable yang menampilkan baris berisi tombol login Google dan Facebook berdampingan.
 * Menggunakan SocialLoginButton secara internal yang sudah menggunakan warna tema.
 * PENTING: Harus digunakan di dalam lingkup `AppTheme`.
 *
 * @param onGoogleClick Aksi saat tombol Google diklik.
 * @param onFacebookClick Aksi saat tombol Facebook diklik.
 * @param modifier Modifier untuk diterapkan pada Row container.
 * @param horizontalArrangement Pengaturan horizontal tombol dalam Row. Default Center.
 * @param spacing Jarak antara tombol Google dan Facebook. Default 16.dp.
 */
@Composable
fun GoogleFacebookLoginRow(
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    spacing: Dp = 16.dp
) {
    Row(
        modifier = modifier, // Terapkan modifier pada Row
        horizontalArrangement = horizontalArrangement, // Gunakan arrangement dari parameter
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Tombol Google menggunakan SocialLoginButton (yang sudah themed)
        SocialLoginButton(
            onClick = onGoogleClick,
            iconRes = Res.drawable.ic_google, // Pastikan path resource benar
            contentDescription = "Login/Sign up with Google"
            // parameter warna dan bentuk menggunakan default dari SocialLoginButton
        )

        Spacer(modifier = Modifier.width(spacing)) // Gunakan spacing dari parameter

        // Tombol Facebook menggunakan SocialLoginButton (yang sudah themed)
        SocialLoginButton(
            onClick = onFacebookClick,
            iconRes = Res.drawable.ic_facebook, // Pastikan path resource benar
            contentDescription = "Login/Sign up with Facebook"
            // parameter warna dan bentuk menggunakan default dari SocialLoginButton
        )
    }
}
