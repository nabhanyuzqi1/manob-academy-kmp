package com.mnb.manobacademy.features.auth.ui // Sesuaikan package

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState // Import jika perlu scroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll // Import jika perlu scroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubbleOutline // Ikon SMS
import androidx.compose.material.icons.filled.Email // Ikon Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
// Hapus: import androidx.compose.ui.graphics.Color (jika tidak digunakan langsung)
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mnb.manobacademy.features.auth.component.ForgotPasswordComponent
import com.mnb.manobacademy.features.auth.component.ResetMethod
import com.mnb.manobacademy.ui.components.PrimaryActionButton
// Import komponen & utilitas
import com.mnb.manobacademy.ui.theme.dimens
// Import fungsi expect untuk tinggi layar
import com.mnb.manobacademy.getScreenHeightDp // <<< IMPORT FUNGSI EXPECT
// Import Resources
import manobacademykmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Composable untuk layar Lupa Kata Sandi.
 * Menyesuaikan padding atas pada layar compact yang tinggi.
 *
 * @param component Instance dari ForgotPasswordComponent.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(component: ForgotPasswordComponent) {
    val state by component.state.subscribeAsState()
    val dimens = MaterialTheme.dimens

    // Dapatkan tinggi layar menggunakan fungsi expect
    val screenHeight = getScreenHeightDp()
    // Tentukan ambang batas tinggi untuk penyesuaian (sesuaikan nilai ini)
    val tallScreenThreshold = 700.dp
    // Hitung padding atas tambahan jika layar tinggi
    val extraTopPadding = if (screenHeight > tallScreenThreshold) 32.dp else 0.dp

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.forgot_password_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = component::onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.forgot_password_back_button_desc)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface, // Atau surfaceContainer untuk M3
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            // Kolom untuk tombol aksi di bagian bawah
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .navigationBarsPadding() // Padding untuk navigation bar
                    .padding(horizontal = dimens.paddingHuge) // Padding horizontal
                    .padding(bottom = dimens.paddingHuge) // Padding bawah sedikit
            ) {
                PrimaryActionButton(
                    text = stringResource(Res.string.forgot_password_reset_button),
                    onClick = component::onResetClicked,
                    enabled = state.isResetEnabled && !state.isLoading, // Aktif jika metode dipilih & tidak loading
                    modifier = Modifier.fillMaxWidth() // Tombol mengisi lebar
                )
                // Tampilkan indikator loading jika perlu
                if (state.isLoading) {
                    Spacer(modifier = Modifier.height(dimens.spacingMedium))
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        // Kolom utama untuk konten layar, memungkinkan scroll jika perlu
        Column(
            modifier = Modifier
                .fillMaxSize() // Mengisi tinggi dan lebar yang tersedia
                .padding(paddingValues) // Padding dari Scaffold (termasuk TopBar)
                .verticalScroll(rememberScrollState()) // Aktifkan scroll vertikal
                .padding(horizontal = dimens.paddingHuge), // Padding horizontal konten
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Spacer tambahan di atas untuk layar tinggi
            Spacer(modifier = Modifier.height(extraTopPadding)) // <<< GUNAKAN PADDING TAMBAHAN

            // Spacer standar di atas ilustrasi
            Spacer(modifier = Modifier.height(dimens.spacingLarge))

            // Ilustrasi
            Image(
                painter = painterResource(Res.drawable.ic_forgotpassword_frame), // Ganti dengan resource Anda
                contentDescription = stringResource(Res.string.forgot_password_illustration_desc),
                modifier = Modifier.height(dimens.illustrationSizeLarge) // Ukuran dari Dimens
            )

            Spacer(modifier = Modifier.height(dimens.spacingHuge))

            // Teks Prompt
            Text(
                text = stringResource(Res.string.forgot_password_prompt),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(dimens.spacingHuge))

            // Pilihan Kontak SMS
            ContactSelectionCard(
                title = stringResource(Res.string.forgot_password_option_sms_title),
                detail = state.maskedPhoneNumber ?: "-",
                icon = Icons.Default.ChatBubbleOutline,
                iconDesc = stringResource(Res.string.forgot_password_sms_icon_desc),
                isSelected = state.selectedMethod == ResetMethod.SMS,
                onClick = { component.onMethodSelected(ResetMethod.SMS) }
            )

            Spacer(modifier = Modifier.height(dimens.selectionCardSpacing)) // Jarak antar kartu

            // Pilihan Kontak Email
            ContactSelectionCard(
                title = stringResource(Res.string.forgot_password_option_email_title),
                detail = state.maskedEmail ?: "-",
                icon = Icons.Default.Email,
                iconDesc = stringResource(Res.string.forgot_password_email_icon_desc),
                isSelected = state.selectedMethod == ResetMethod.EMAIL,
                onClick = { component.onMethodSelected(ResetMethod.EMAIL) }
            )

            // Spacer tambahan di bawah sebelum BottomBar (opsional, tergantung kebutuhan)
            Spacer(modifier = Modifier.height(dimens.spacingLarge))

        } // Akhir Column konten utama
    } // Akhir Scaffold
}

/**
 * Composable kustom untuk kartu pilihan kontak (SMS/Email).
 * (Tidak ada perubahan di sini)
 */
@Composable
private fun ContactSelectionCard(
    title: String,
    detail: String,
    icon: ImageVector,
    iconDesc: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimens = MaterialTheme.dimens
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline // Gunakan outline biasa jika tidak terpilih
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface // Gunakan surface jika tidak terpilih

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.selectionCardCornerRadius))
            .border(
                width = if (isSelected) 2.dp else 1.dp, // Border lebih tebal jika terpilih
                color = borderColor,
                shape = RoundedCornerShape(dimens.selectionCardCornerRadius)
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(dimens.selectionCardCornerRadius),
        color = backgroundColor // Warna background kartu
    ) {
        Row(
            modifier = Modifier.padding(dimens.selectionCardPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ikon dalam lingkaran
            Box(
                modifier = Modifier
                    .size(dimens.selectionCardIconSize + dimens.selectionCardIconPadding * 2) // Ukuran box = ikon + padding
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh), // Background ikon sedikit berbeda
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = iconDesc,
                    modifier = Modifier.size(dimens.selectionCardIconSize),
                    tint = MaterialTheme.colorScheme.primary // Warna ikon tetap primary
                )
            }

            Spacer(modifier = Modifier.width(dimens.spacingLarge))

            // Kolom untuk teks
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium, // Mungkin labelLarge lebih cocok?
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(dimens.spacingSmall))
                Text(
                    text = detail,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
