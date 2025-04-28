package com.mnb.manobacademy.features.auth.ui // Sesuaikan dengan package utama Anda

// Import komponen & utilitas
// Import Resources
import androidx.compose.foundation.layout.* // Wildcard import ok
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Gunakan ikon auto-mirrored
import androidx.compose.material3.* // Wildcard import ok
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.mnb.manobacademy.features.auth.ui.components.OtpInputFields
// Pastikan import PrimaryActionButton benar
import com.mnb.manobacademy.ui.components.PrimaryActionButton
import com.mnb.manobacademy.ui.theme.dimens // <- Import helper dimens
import manobacademykmp.composeapp.generated.resources.* // <- Import semua resource
import org.jetbrains.compose.resources.stringResource // <- Import stringResource

/**
 * Composable untuk layar verifikasi kode OTP.
 * Tombol Verifikasi menempel di bawah, teks Kirim Ulang di bawah input OTP.
 *
 * ... (KDoc lainnya tetap sama) ...
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
                    containerColor = MaterialTheme.colorScheme.surface,
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
                    .padding(horizontal = dimens.paddingHuge) // Padding horizontal
                    .padding(bottom = dimens.paddingHuge) // Padding bawah
            ) {
                PrimaryActionButton(
                    text = stringResource(Res.string.otp_verify_button),
                    onClick = {
                        if (isOtpComplete) {
                            onVerifyClick(otpCode)
                        }
                    },
                    enabled = isOtpComplete
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        // Konten utama di dalam Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Terapkan padding dari Scaffold (termasuk bottom padding dari bottomBar)
                .padding(horizontal = dimens.paddingHuge), // Padding horizontal utama
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                otpLength = 6,
                onOtpFilled = { code ->
                    otpCode = code
                    isOtpComplete = true
                },
                modifier = Modifier.padding(horizontal = dimens.paddingSmall)
            )

            Spacer(modifier = Modifier.height(dimens.spacingLarge)) // Jarak setelah input OTP

            // --- Teks dan Link Kirim Ulang dipindah ke sini ---
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
            // ----------------------------------------------------

            // Hapus Spacer weight(1f) yang sebelumnya mendorong ke bawah
            // Spacer(modifier = Modifier.weight(1f))

            // Tombol Verifikasi sudah dipindah ke bottomBar
        }
    }
}
