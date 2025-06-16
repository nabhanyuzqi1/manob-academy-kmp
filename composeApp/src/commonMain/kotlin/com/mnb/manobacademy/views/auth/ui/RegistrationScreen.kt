package com.mnb.manobacademy.views.auth.ui // Sesuaikan dengan package utama Anda

// Import Compose & Material Lengkap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // Wildcard import ok
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.* // Wildcard import ok
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
// Import baru untuk unit Dp
import androidx.compose.ui.unit.dp
// Import Composable Kustom yang Telah Dipisah
import com.mnb.manobacademy.views.auth.component.GoogleFacebookLoginRow
import com.mnb.manobacademy.ui.components.StyledOutlinedTextField
import com.mnb.manobacademy.ui.components.PrimaryActionButton
// Import Tema & Utilitas
import com.mnb.manobacademy.ui.theme.dimens // <- Import helper dimens
import com.mnb.manobacademy.util.PlatformType
import com.mnb.manobacademy.util.currentPlatform
// Import fungsi expect untuk tinggi layar
import com.mnb.manobacademy.util.getScreenHeightDp // <<< IMPORT FUNGSI EXPECT
// Import Resources
import manobacademykmp.composeapp.generated.resources.* // <- Import semua resource
import org.jetbrains.compose.resources.stringResource // <- Import stringResource

/**
 * Composable utama untuk layar Registrasi.
 * Menggunakan String Resources, Dimensions, dan komponen modular dari Tema.
 * Menyesuaikan padding atas pada layar compact yang tinggi.
 *
 * PENTING: Composable ini HARUS dipanggil dari dalam wrapper `AppTheme` di level aplikasi.
 *
 * @param onNavigateToLogin Lambda untuk navigasi ke layar login.
 * @param onRegisterSuccess Lambda yang dipanggil saat registrasi berhasil (setelah validasi).
 */
@Composable
fun RegistrationScreen(
    onRegisterSuccess: (String) -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // State untuk menyimpan input form - Dideklarasikan satu kali
        var fullName by remember { mutableStateOf("") }
        var phoneNumber by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var termsAccepted by remember { mutableStateOf(false) }

        // --- Logika Pemisahan UI Berdasarkan Platform ---
        if (currentPlatform == PlatformType.DESKTOP) {
            // --- Layout untuk Platform Desktop ---
            DesktopRegisterLayout(
                fullName = fullName,
                phoneNumber = phoneNumber,
                email = email,
                password = password,
                termsAccepted = termsAccepted,
                onFullNameChange = { fullName = it },
                onPhoneNumberChange = { phoneNumber = it },
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onTermsAcceptedChange = { termsAccepted = it },
                onRegisterSuccess = onRegisterSuccess, // Diteruskan langsung
                onNavigateToLogin = onNavigateToLogin
            )
        } else {
            // --- Layout untuk Platform Mobile / Lainnya ---
            MobileRegisterLayout(
                fullName = fullName,
                phoneNumber = phoneNumber,
                email = email,
                password = password,
                termsAccepted = termsAccepted,
                onFullNameChange = { fullName = it },
                onPhoneNumberChange = { phoneNumber = it },
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onTermsAcceptedChange = { termsAccepted = it },
                onRegisterSuccess = onRegisterSuccess, // Diteruskan langsung
                onNavigateToLogin = onNavigateToLogin
            )
        }
    }
}

/**
 * Composable terpisah untuk tata letak layar registrasi di platform Desktop.
 */
@Composable
private fun DesktopRegisterLayout(
    fullName: String,
    phoneNumber: String,
    email: String,
    password: String,
    termsAccepted: Boolean,
    onFullNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTermsAcceptedChange: (Boolean) -> Unit,
    // --- PERBAIKAN 1 ---
    // Mengubah tipe lambda untuk menerima email (String)
    onRegisterSuccess: (String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val dimens = MaterialTheme.dimens

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(dimens.desktopFormMaxWidth) // Gunakan dimensi tema
                .fillMaxHeight()
                .padding(vertical = dimens.paddingLarge), // Gunakan dimensi tema
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- BAGIAN 1: ATAS ---
            Spacer(modifier = Modifier.height(dimens.spacingGiant))
            Text(
                stringResource(Res.string.register_greeting),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth().padding(horizontal = dimens.paddingHuge)
            )
            Spacer(modifier = Modifier.height(dimens.spacingSmall))
            Text(
                stringResource(Res.string.register_title),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth().padding(horizontal = dimens.paddingHuge)
            )
            Spacer(modifier = Modifier.height(dimens.spacingHuge))

            // --- BAGIAN 2: TENGAH (Scrollable) ---
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = dimens.paddingHuge)
            ) {
                RegisterFormFields(
                    fullNameValue = fullName,
                    phoneNumberValue = phoneNumber,
                    emailValue = email,
                    passwordValue = password,
                    termsAcceptedValue = termsAccepted,
                    onFullNameChange = onFullNameChange,
                    onPhoneNumberChange = onPhoneNumberChange,
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange,
                    onTermsAcceptedChange = onTermsAcceptedChange,
                    // Meneruskan lambda yang sudah diperbaiki
                    onRegisterClick = onRegisterSuccess
                )
            } // Akhir Column Scrollable

            // --- BAGIAN 3: BAWAH ---
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimens.paddingLarge,
                        vertical = dimens.bottomRowPaddingVertical
                    )
            ) {
                Text(
                    text = stringResource(Res.string.register_login_prompt),
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(onClick = onNavigateToLogin) {
                    Text(
                        text = stringResource(Res.string.register_login_link),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}


/**
 * Composable terpisah untuk tata letak layar registrasi di platform Mobile/Lainnya.
 * Menyesuaikan padding atas pada layar compact yang tinggi.
 */
@Composable
private fun MobileRegisterLayout(
    fullName: String,
    phoneNumber: String,
    email: String,
    password: String,
    termsAccepted: Boolean,
    onFullNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTermsAcceptedChange: (Boolean) -> Unit,
    // --- PERBAIKAN 2 ---
    // Mengubah tipe lambda untuk menerima email (String)
    onRegisterSuccess: (String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val dimens = MaterialTheme.dimens
    val screenHeight = getScreenHeightDp()
    val tallScreenThreshold = 700.dp
    val extraTopPadding = if (screenHeight > tallScreenThreshold) 32.dp else 0.dp

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- BAGIAN 1: ATAS ---
        Spacer(modifier = Modifier.height(extraTopPadding))
        Spacer(modifier = Modifier.height(dimens.topSpacingMobile))
        Text(
            stringResource(Res.string.register_greeting),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth().padding(horizontal = dimens.paddingHuge),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimens.spacingSmall))
        Text(
            stringResource(Res.string.register_title),
            style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(horizontal = dimens.paddingHuge),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimens.spacingHuge))

        // --- BAGIAN 2: TENGAH (Scrollable & Weighted) ---
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = dimens.paddingHuge)
        ) {
            RegisterFormFields(
                fullNameValue = fullName,
                phoneNumberValue = phoneNumber,
                emailValue = email,
                passwordValue = password,
                termsAcceptedValue = termsAccepted,
                onFullNameChange = onFullNameChange,
                onPhoneNumberChange = onPhoneNumberChange,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onTermsAcceptedChange = onTermsAcceptedChange,
                // Meneruskan lambda yang sudah diperbaiki
                onRegisterClick = onRegisterSuccess,
            )
        } // Akhir Column Scrollable

        // --- BAGIAN 3: BAWAH ---
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    horizontal = dimens.paddingLarge,
                    vertical = dimens.bottomRowPaddingVertical
                )
        ) {
            Text(
                text = stringResource(Res.string.register_login_prompt),
                color = MaterialTheme.colorScheme.onBackground
            )
            TextButton(onClick = onNavigateToLogin) {
                Text(
                    text = stringResource(Res.string.register_login_link),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


/**
 * Composable yang berisi elemen-elemen form registrasi.
 * Menggunakan String Resources, Dimensions, dan komponen modular dari Tema.
 */
@Composable
private fun RegisterFormFields(
    fullNameValue: String,
    phoneNumberValue: String,
    emailValue: String,
    passwordValue: String,
    termsAcceptedValue: Boolean,
    onFullNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTermsAcceptedChange: (Boolean) -> Unit,
    // --- PERBAIKAN 3 ---
    // Mengubah tipe lambda untuk menerima email (String)
    onRegisterClick: (String) -> Unit
) {
    val dimens = MaterialTheme.dimens

    // Full Name Input Field
    StyledOutlinedTextField(
        value = fullNameValue,
        onValueChange = onFullNameChange,
        label = { Text(stringResource(Res.string.register_fullname_label)) },
        leadingIcon = { Icon(Icons.Filled.Person, stringResource(Res.string.register_fullname_icon_desc), tint = MaterialTheme.colorScheme.onSurfaceVariant) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(dimens.spacingLarge))

    // Phone Number Input Field
    StyledOutlinedTextField(
        value = phoneNumberValue,
        onValueChange = onPhoneNumberChange,
        label = { Text(stringResource(Res.string.register_phone_label)) },
        leadingIcon = { Icon(Icons.Filled.Phone, stringResource(Res.string.register_phone_icon_desc), tint = MaterialTheme.colorScheme.onSurfaceVariant) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(dimens.spacingLarge))

    // Email Input Field
    StyledOutlinedTextField(
        value = emailValue,
        onValueChange = onEmailChange,
        label = { Text(stringResource(Res.string.register_email_label)) },
        leadingIcon = { Icon(Icons.Filled.Email, stringResource(Res.string.register_email_icon_desc), tint = MaterialTheme.colorScheme.onSurfaceVariant) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(dimens.spacingLarge))

    // Password Input Field
    StyledOutlinedTextField(
        value = passwordValue,
        onValueChange = onPasswordChange,
        label = { Text(stringResource(Res.string.register_password_label)) },
        leadingIcon = { Icon(Icons.Filled.Lock, stringResource(Res.string.register_password_icon_desc), tint = MaterialTheme.colorScheme.onSurfaceVariant) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(dimens.spacingLarge))

    // Checkbox Persetujuan Syarat & Ketentuan
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable { onTermsAcceptedChange(!termsAcceptedValue) }
            .padding(vertical = dimens.spacingSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = termsAcceptedValue,
            onCheckedChange = onTermsAcceptedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                checkmarkColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        Spacer(modifier = Modifier.width(dimens.spacingMedium))
        Text(
            text = stringResource(Res.string.register_terms_prompt),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    Spacer(modifier = Modifier.height(dimens.spacingHuge))

    // Tombol Register Utama
    PrimaryActionButton(
        text = stringResource(Res.string.register_button_register),
        // --- PERBAIKAN 4 ---
        // Panggil onRegisterClick dengan nilai email saat ini
        onClick = { onRegisterClick(emailValue) },
        modifier = Modifier.fillMaxWidth(),
        enabled = termsAcceptedValue,
        loading = false
    )

    Spacer(modifier = Modifier.height(dimens.spacingLarge))

    // Divider dan Teks Pemisah
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = dimens.dividerThickness,
            color = MaterialTheme.colorScheme.outlineVariant
        )
        Text(
            text = stringResource(Res.string.register_divider_text),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = dimens.paddingMedium)
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = dimens.dividerThickness,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }

    Spacer(modifier = Modifier.height(dimens.spacingLarge))

    // Gunakan Komponen Social Login Buttons
    GoogleFacebookLoginRow(
        modifier = Modifier.fillMaxWidth(),
        onGoogleClick = { /* TODO: Implement Google sign up */ },
        onFacebookClick = { /* TODO: Implement Facebook sign up */ }
    )

    Spacer(modifier = Modifier.height(dimens.spacingMedium))
}