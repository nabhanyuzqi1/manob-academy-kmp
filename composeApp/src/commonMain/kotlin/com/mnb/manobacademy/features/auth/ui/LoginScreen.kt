package com.mnb.manobacademy.features.auth.ui // Sesuaikan dengan package utama Anda

// Import Compose & Material Lengkap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // Wildcard import ok
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.* // Wildcard import ok
import androidx.compose.runtime.* // Wildcard import ok
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// HAPUS: import androidx.compose.ui.platform.LocalConfiguration (jika ada)
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
// Import baru untuk unit Dp
import androidx.compose.ui.unit.dp
// Import Utilitas Platform & Resource Anda
import com.mnb.manobacademy.util.PlatformType // Sesuaikan path jika perlu
import com.mnb.manobacademy.util.currentPlatform // Sesuaikan path jika perlu
// Import fungsi expect untuk tinggi layar
import com.mnb.manobacademy.getScreenHeightDp
import manobacademykmp.composeapp.generated.resources.Res // Sesuaikan path jika perlu
import manobacademykmp.composeapp.generated.resources.logo_manob_academy_notext // Sesuaikan path jika perlu
import org.jetbrains.compose.resources.painterResource

// Import Composable Kustom yang Telah Dipisah
import com.mnb.manobacademy.features.auth.ui.components.StyledOutlinedTextField // <- PASTIKAN PATH INI BENAR
import com.mnb.manobacademy.features.auth.ui.components.GoogleFacebookLoginRow // <- IMPORT KOMPONEN BARU
import com.mnb.manobacademy.ui.components.PrimaryActionButton
import com.mnb.manobacademy.ui.theme.dimens // Import helper dimens
// Import String Resources
import manobacademykmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource


/**
 * Composable utama untuk layar Login.
 * Menangani logika tampilan antara Desktop dan Mobile.
 *
 * @param onNavigateToRegister Lambda untuk navigasi ke layar registrasi.
 * @param onLoginSuccess Lambda yang dipanggil saat login berhasil.
 * @param onForgotPasswordClick Lambda untuk navigasi ke layar lupa kata sandi.
 */
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    // Mengambil dimensi dari tema Material
    val dimens = MaterialTheme.dimens

    // State untuk field email dan password
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Memilih layout berdasarkan platform saat ini
        if (currentPlatform == PlatformType.DESKTOP) {
            // --- Layout Desktop ---
            DesktopLoginLayout(
                email = email,
                password = password,
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onLoginSuccess = onLoginSuccess,
                onNavigateToRegister = onNavigateToRegister,
                onForgotPasswordClick = onForgotPasswordClick
            )
        } else {
            // --- Layout Mobile ---
            MobileLoginLayout(
                email = email,
                password = password,
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onLoginSuccess = onLoginSuccess,
                onNavigateToRegister = onNavigateToRegister,
                onForgotPasswordClick = onForgotPasswordClick
            )
        }
    }
}

/**
 * Composable untuk layout Login di platform Desktop.
 */
@Composable
private fun DesktopLoginLayout(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    val dimens = MaterialTheme.dimens

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Pusatkan form di layar
    ) {
        Column(
            modifier = Modifier
                .width(dimens.desktopFormMaxWidth) // Batasi lebar form
                .fillMaxHeight() // Gunakan tinggi penuh
                .padding(vertical = dimens.paddingLarge), // Padding vertikal
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- BAGIAN 1: ATAS (Logo & Judul) ---
            Spacer(modifier = Modifier.height(dimens.topSpacingDesktop))
            Image(
                painterResource(Res.drawable.logo_manob_academy_notext),
                contentDescription = stringResource(Res.string.logo_content_description),
                modifier = Modifier.size(dimens.logoSizeSmall)
            )
            Spacer(modifier = Modifier.height(dimens.spacingMedium))
            Text(
                stringResource(Res.string.login_header_title),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                stringResource(Res.string.login_header_subtitle),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(dimens.spacingExtraLarge))

            // --- BAGIAN 2: TENGAH (Form Fields) ---
            // Gunakan weight untuk mengisi ruang vertikal yang tersisa
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center, // Pusatkan form secara vertikal
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val scrollState = rememberScrollState() // Memungkinkan scroll jika konten melebihi
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(
                            horizontal = dimens.paddingHuge, // Padding horizontal dalam form
                            vertical = dimens.formInternalPaddingVertical // Padding vertikal dalam form
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    // Memanggil composable terpisah untuk field form
                    LoginFormFields(
                        emailValue = email,
                        passwordValue = password,
                        onEmailChange = onEmailChange,
                        onPasswordChange = onPasswordChange,
                        onLoginClick = onLoginSuccess,
                        onForgotPasswordClick = onForgotPasswordClick
                    )
                }
            }

            // --- BAGIAN 3: BAWAH (Link Sign Up) ---
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimens.paddingLarge,
                        vertical = dimens.bottomRowPaddingVertical // Padding vertikal di bagian bawah
                    )
            ) {
                Text(
                    text = stringResource(Res.string.login_signup_prompt),
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(onClick = onNavigateToRegister) {
                    Text(
                        text = stringResource(Res.string.login_signup_link),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}


/**
 * Composable untuk layout Login di platform Mobile.
 * Menggunakan getScreenHeightDp() untuk menyesuaikan spasi atas pada layar tinggi.
 */
@Composable
private fun MobileLoginLayout(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    val dimens = MaterialTheme.dimens
    // Gunakan fungsi expect untuk mendapatkan tinggi layar secara platform-agnostik
    val screenHeight = getScreenHeightDp()

    // Tentukan ambang batas tinggi untuk penyesuaian spasi
    val tallScreenThreshold = 700.dp // Sesuaikan nilai ini berdasarkan pengujian

    // Hitung spasi atas berdasarkan tinggi layar
    val actualTopSpacing = if (screenHeight > tallScreenThreshold) {
        dimens.topSpacingMobile + 32.dp // Tambah spasi jika layar tinggi
    } else {
        dimens.topSpacingMobile // Gunakan spasi standar jika tidak
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- BAGIAN 1: ATAS (Logo & Judul) ---
        Spacer(modifier = Modifier.height(actualTopSpacing)) // Gunakan spasi yang dihitung
        Image(
            painterResource(Res.drawable.logo_manob_academy_notext),
            contentDescription = stringResource(Res.string.logo_content_description),
            modifier = Modifier.size(dimens.logoSizeLarge) // Ukuran logo dari Dimens
        )
        Spacer(modifier = Modifier.height(dimens.spacingMedium))
        Text(
            stringResource(Res.string.login_header_title),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            stringResource(Res.string.login_header_subtitle),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(dimens.spacingExtraLarge))

        // --- BAGIAN 2: TENGAH (Form Fields) ---
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(1f) // Mengisi ruang vertikal yang tersisa
                .fillMaxWidth()
                .verticalScroll(scrollState) // Memungkinkan scroll
                .imePadding() // Menangani inset keyboard
                .padding(
                    horizontal = dimens.paddingHuge, // Padding horizontal dalam form
                    vertical = dimens.formInternalPaddingVertical // Padding vertikal dalam form
                )
        ) {
            // Memanggil composable terpisah untuk field form
            LoginFormFields(
                emailValue = email,
                passwordValue = password,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onLoginClick = onLoginSuccess,
                onForgotPasswordClick = onForgotPasswordClick
            )
        }

        // --- BAGIAN 3: BAWAH (Link Sign Up) ---
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    horizontal = dimens.paddingLarge,
                    vertical = dimens.bottomRowPaddingVertical // Padding vertikal di bagian bawah
                )
        ) {
            Text(
                text = stringResource(Res.string.login_signup_prompt),
                color = MaterialTheme.colorScheme.onBackground
            )
            TextButton(onClick = onNavigateToRegister) {
                Text(
                    text = stringResource(Res.string.login_signup_link),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


/**
 * Composable terpisah untuk menampilkan field input form login,
 * tombol aksi, dan opsi login sosial.
 */
@Composable
private fun LoginFormFields(
    emailValue: String,
    passwordValue: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    val dimens = MaterialTheme.dimens

    // Field Email
    StyledOutlinedTextField(
        value = emailValue,
        onValueChange = onEmailChange,
        label = { Text(stringResource(Res.string.login_email_label)) },
        leadingIcon = { Icon(Icons.Filled.Email, stringResource(Res.string.login_email_icon_desc), tint = MaterialTheme.colorScheme.onSurfaceVariant) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(dimens.spacingLarge))

    // Field Password
    StyledOutlinedTextField(
        value = passwordValue,
        onValueChange = onPasswordChange,
        label = { Text(stringResource(Res.string.login_password_label)) },
        leadingIcon = { Icon(Icons.Filled.Lock, stringResource(Res.string.login_password_icon_desc), tint = MaterialTheme.colorScheme.onSurfaceVariant) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    // Tombol Lupa Kata Sandi
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End // Rata kanan
    ) {
        TextButton(onClick = onForgotPasswordClick) {
            Text(
                stringResource(Res.string.login_forgot_password),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    Spacer(modifier = Modifier.height(dimens.spacingMassive)) // Spasi besar sebelum tombol utama

    // Tombol Login Utama
    PrimaryActionButton(
        text = stringResource(Res.string.login_button_signin),
        onClick = onLoginClick,
        modifier = Modifier.fillMaxWidth() // Tombol mengisi lebar penuh
    )

    Spacer(modifier = Modifier.height(dimens.spacingExtraLarge))

    // Pemisah "Atau masuk dengan"
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f), // Garis mengisi ruang kiri
            thickness = dimens.dividerThickness,
            color = MaterialTheme.colorScheme.outlineVariant
        )
        Text(
            text = stringResource(Res.string.login_divider_text),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = dimens.paddingMedium) // Padding teks pemisah
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f), // Garis mengisi ruang kanan
            thickness = dimens.dividerThickness,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }


    Spacer(modifier = Modifier.height(dimens.spacingLarge))

    // Tombol Login Google & Facebook
    GoogleFacebookLoginRow(
        modifier = Modifier.fillMaxWidth(),
        onGoogleClick = { /* TODO: Implement Google Login */ },
        onFacebookClick = { /* TODO: Implement Facebook Login */ }
    )

    Spacer(modifier = Modifier.height(dimens.spacingLarge)) // Spasi tambahan di bawah
}
