package com.mnb.manobacademy.features.auth.ui // Sesuaikan dengan package utama Anda

// Import komponen & utilitas
// Import Resources
import androidx.compose.foundation.layout.* // Wildcard import ok
import androidx.compose.foundation.rememberScrollState // <<< Import untuk scroll
import androidx.compose.foundation.verticalScroll // <<< Import untuk scroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Gunakan ikon auto-mirrored
import androidx.compose.material3.* // Wildcard import ok
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
// Import baru untuk unit Dp
import androidx.compose.ui.unit.dp
import com.mnb.manobacademy.features.auth.ui.components.OtpInputFields
// Pastikan import PrimaryActionButton benar
import com.mnb.manobacademy.ui.components.PrimaryActionButton
import com.mnb.manobacademy.ui.theme.dimens // <- Import helper dimens
// Import fungsi expect untuk tinggi layar
import com.mnb.manobacademy.getScreenHeightDp // <<< IMPORT FUNGSI EXPECT
import manobacademykmp.composeapp.generated.resources.* // <- Import semua resource
import org.jetbrains.compose.resources.stringResource // <- Import stringResource

/**
 * Composable untuk layar verifikasi kode OTP.
 * Tombol Verifikasi menempel di bawah, teks Kirim Ulang di bawah input OTP.
 * Menyesuaikan padding atas pada layar compact yang tinggi.
 *
 * @param onNavigateBack Lambda untuk navigasi kembali.
 * @param onVerifyClick Lambda yang dipanggil saat tombol Verifikasi diklik dengan kode OTP.
 * @param onResendClick Lambda yang dipanggil saat link Kirim Ulang diklik.
 * @param emailAddress Opsional, alamat email yang ditampilkan di prompt.
 */
@OptIn(ExperimentalMaterial3Api::class) // Untuk TopAppBar
@Composable
fun VerificationCodeScreen(
    onNavigateBack: () -> Unit,
    onVerifyClick: (String) -> Unit,
    onResendClick: () -> Unit,
    emailAddress: String? = null // Opsional, bisa ditampilkan di teks prompt
) {
    // Akses dimensi dari tema
    val dimens = MaterialTheme.dimens
    var otpCode by remember { mutableStateOf("") }
    var isOtpComplete by remember { mutableStateOf(false) }

    // Dapatkan tinggi layar menggunakan fungsi expect
    val screenHeight = getScreenHeightDp()
    // Tentukan ambang batas tinggi untuk penyesuaian (sesuaikan nilai ini)
    val tallScreenThreshold = 700.dp
    // Hitung padding atas tambahan jika layar tinggi
    val extraTopPadding = if (screenHeight > tallScreenThreshold) 32.dp else 0.dp
    // Tentukan panjang OTP yang diharapkan
    val expectedOtpLength = 6

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.otp_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.otp_back_button_desc)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface, // Atau surfaceContainer
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        // Tempatkan Tombol Verifikasi di bottomBar
        bottomBar = {
            // Gunakan Column atau Box untuk padding di sekitar tombol
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding() // <<< Padding untuk navigation bar
                    .padding(horizontal = dimens.paddingHuge) // Padding horizontal
                    .padding(bottom = dimens.paddingExtraLarge) // Padding bawah sedikit
            ) {
                PrimaryActionButton(
                    text = stringResource(Res.string.otp_verify_button),
                    onClick = {
                        if (isOtpComplete) {
                            onVerifyClick(otpCode)
                        }
                    },
                    enabled = isOtpComplete,
                    modifier = Modifier.fillMaxWidth() // Tombol mengisi lebar
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        // Konten utama di dalam Column, memungkinkan scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Terapkan padding dari Scaffold
                .verticalScroll(rememberScrollState()) // <<< Aktifkan scroll vertikal
                .padding(horizontal = dimens.paddingHuge), // Padding horizontal utama
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Spacer tambahan di atas untuk layar tinggi
            Spacer(modifier = Modifier.height(extraTopPadding)) // <<< GUNAKAN PADDING TAMBAHAN

            // Spacer standar di atas teks prompt
            Spacer(modifier = Modifier.height(dimens.spacingGiant)) // Jarak dari app bar

            // Teks Prompt
            Text(
                text = stringResource(Res.string.otp_prompt) + (emailAddress?.let { "\n$it" } ?: ""),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(dimens.spacingMassive)) // Jarak ke input OTP

            // Input Fields OTP
            OtpInputFields(
                otpLength = expectedOtpLength, // Gunakan variabel panjang OTP
                onOtpFilled = { code -> // <<< PERBAIKI LAMBDA: Hanya menerima 'code' (String)
                    otpCode = code
                    // Set isOtpComplete berdasarkan panjang kode yang diterima
                    isOtpComplete = code.length == expectedOtpLength
                },
                modifier = Modifier.padding(horizontal = dimens.paddingSmall) // Padding agar tidak terlalu mepet
            )

            Spacer(modifier = Modifier.height(dimens.spacingLarge)) // Jarak setelah input OTP

            // Teks dan Link Kirim Ulang
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(Res.string.otp_didnt_receive),
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(onClick = onResendClick) {
                    Text(
                        text = stringResource(Res.string.otp_resend_link),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Spacer tambahan di bawah sebelum BottomBar (opsional)
            Spacer(modifier = Modifier.height(dimens.spacingLarge))

        } // Akhir Column konten utama
    } // Akhir Scaffold
}
