package com.mnb.manobacademy.features.auth.ui // Sesuaikan dengan package utama Anda

// Import Compose & Material Lengkap
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
// Import Composable Kustom yang Telah Dipisah
import com.mnb.manobacademy.features.auth.ui.components.GoogleFacebookLoginRow
import com.mnb.manobacademy.features.auth.ui.components.StyledOutlinedTextField
import com.mnb.manobacademy.ui.components.PrimaryActionButton
// Import Tema & Utilitas
import com.mnb.manobacademy.ui.theme.dimens // <- Import helper dimens
import com.mnb.manobacademy.util.PlatformType
import com.mnb.manobacademy.util.currentPlatform
// Import Resources
import manobacademykmp.composeapp.generated.resources.* // <- Import semua resource
import org.jetbrains.compose.resources.stringResource // <- Import stringResource

/**
 * Composable utama untuk layar Registrasi.
 * Menggunakan String Resources, Dimensions, dan komponen modular dari Tema.
 *
 * PENTING: Composable ini HARUS dipanggil dari dalam wrapper `AppTheme` di level aplikasi.
 *
 * @param onNavigateToLogin Lambda untuk navigasi ke layar login.
 * @param onRegisterSuccess Lambda yang dipanggil saat registrasi berhasil (setelah validasi).
 */
@Composable
fun RegistrationScreen(
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    // Akses dimensi dari tema
    val dimens = MaterialTheme.dimens

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // State untuk menyimpan input form
        var fullName by remember { mutableStateOf("") }
        var phoneNumber by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var termsAccepted by remember { mutableStateOf(false) }

        // --- Logika Pemisahan UI Berdasarkan Platform ---
        if (currentPlatform == PlatformType.DESKTOP) {
            // --- Layout untuk Platform Desktop ---
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
                    Spacer(modifier = Modifier.height(dimens.spacingGiant)) // Jarak lebih besar di desktop?
                    Text(
                        stringResource(Res.string.register_greeting), // Gunakan string resource
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = dimens.paddingHuge) // Gunakan dimensi tema
                    )
                    Spacer(modifier = Modifier.height(dimens.spacingSmall)) // Gunakan dimensi tema
                    Text(
                        stringResource(Res.string.register_title), // Gunakan string resource
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = dimens.paddingHuge) // Gunakan dimensi tema
                    )
                    Spacer(modifier = Modifier.height(dimens.spacingHuge)) // Gunakan dimensi tema

                    // --- BAGIAN 2: TENGAH (Scrollable) ---
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = dimens.paddingHuge) // Gunakan dimensi tema
                    ) {
                        RegisterFormFields(
                            fullNameValue = fullName,
                            phoneNumberValue = phoneNumber,
                            emailValue = email,
                            passwordValue = password,
                            termsAcceptedValue = termsAccepted,
                            onFullNameChange = { fullName = it },
                            onPhoneNumberChange = { phoneNumber = it },
                            onEmailChange = { email = it },
                            onPasswordChange = { password = it },
                            onTermsAcceptedChange = { termsAccepted = it },
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
                                horizontal = dimens.paddingLarge, // Gunakan dimensi tema
                                vertical = dimens.bottomRowPaddingVertical // Gunakan dimensi tema
                            )
                    ) {
                        Text(
                            text = stringResource(Res.string.register_login_prompt), // Gunakan string resource
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        TextButton(onClick = onNavigateToLogin) {
                            Text(
                                text = stringResource(Res.string.register_login_link), // Gunakan string resource
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                } // Akhir dari Column 450dp
            } // Akhir dari Box centering
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
                onRegisterSuccess = onRegisterSuccess,
                onNavigateToLogin = onNavigateToLogin
            )
        }
    }
}

/**
 * Composable terpisah untuk tata letak layar registrasi di platform Mobile/Lainnya.
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
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    // Akses dimensi dari tema
    val dimens = MaterialTheme.dimens

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- BAGIAN 1: ATAS ---
        Spacer(modifier = Modifier.height(dimens.topSpacingMobile)) // Gunakan dimensi tema
        Text(
            stringResource(Res.string.register_greeting), // Gunakan string resource
            style = MaterialTheme.typography.bodyLarge, // Style berbeda untuk mobile?
            modifier = Modifier.fillMaxWidth().padding(horizontal = dimens.paddingHuge), // Gunakan dimensi tema
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimens.spacingSmall)) // Gunakan dimensi tema
        Text(
            stringResource(Res.string.register_title), // Gunakan string resource
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth().padding(horizontal = dimens.paddingHuge), // Gunakan dimensi tema
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimens.spacingHuge)) // Gunakan dimensi tema

        // --- BAGIAN 2: TENGAH (Scrollable & Weighted) ---
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = dimens.paddingHuge) // Gunakan dimensi tema
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
                onRegisterClick = onRegisterSuccess,
            )
        } // Akhir Column Scrollable

        // --- BAGIAN 3: BAWAH ---
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimens.paddingLarge, // Gunakan dimensi tema
                    vertical = dimens.bottomRowPaddingVertical // Gunakan dimensi tema
                )
        ) {
            Text(
                text = stringResource(Res.string.register_login_prompt), // Gunakan string resource
                color = MaterialTheme.colorScheme.onBackground
            )
            TextButton(onClick = onNavigateToLogin) {
                Text(
                    text = stringResource(Res.string.register_login_link), // Gunakan string resource
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
    onRegisterClick: () -> Unit
) {
    // Akses dimensi dari tema
    val dimens = MaterialTheme.dimens

    // Full Name Input Field
    StyledOutlinedTextField(
        value = fullNameValue,
        onValueChange = onFullNameChange,
        label = { Text(stringResource(Res.string.register_fullname_label)) }, // String resource
        leadingIcon = { Icon(Icons.Filled.Person, stringResource(Res.string.register_fullname_icon_desc), tint = MaterialTheme.colorScheme.onSurfaceVariant) }, // String resource
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(dimens.spacingLarge)) // Dimensi tema

    // Phone Number Input Field
    StyledOutlinedTextField(
        value = phoneNumberValue,
        onValueChange = onPhoneNumberChange,
        label = { Text(stringResource(Res.string.register_phone_label)) }, // String resource
        leadingIcon = { Icon(Icons.Filled.Phone, stringResource(Res.string.register_phone_icon_desc), tint = MaterialTheme.colorScheme.onSurfaceVariant) }, // String resource
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(dimens.spacingLarge)) // Dimensi tema

    // Email Input Field
    StyledOutlinedTextField(
        value = emailValue,
        onValueChange = onEmailChange,
        label = { Text(stringResource(Res.string.register_email_label)) }, // String resource
        leadingIcon = { Icon(Icons.Filled.Email, stringResource(Res.string.register_email_icon_desc), tint = MaterialTheme.colorScheme.onSurfaceVariant) }, // String resource
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(dimens.spacingLarge)) // Dimensi tema

    // Password Input Field
    StyledOutlinedTextField(
        value = passwordValue,
        onValueChange = onPasswordChange,
        label = { Text(stringResource(Res.string.register_password_label)) }, // String resource
        leadingIcon = { Icon(Icons.Filled.Lock, stringResource(Res.string.register_password_icon_desc), tint = MaterialTheme.colorScheme.onSurfaceVariant) }, // String resource
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(dimens.spacingLarge)) // Dimensi tema

    // Checkbox Persetujuan Syarat & Ketentuan
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = termsAcceptedValue,
            onCheckedChange = onTermsAcceptedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary, // Warna tema
                uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant, // Warna tema
                checkmarkColor = MaterialTheme.colorScheme.onPrimary // Warna tema
            )
        )
        Spacer(modifier = Modifier.width(dimens.spacingMedium)) // Dimensi tema
        Text(
            text = stringResource(Res.string.register_terms_prompt), // String resource
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant // Warna tema
        )
    }

    Spacer(modifier = Modifier.height(dimens.spacingHuge)) // Dimensi tema

    // Tombol Register Utama - Gunakan PrimaryActionButton
    PrimaryActionButton(
        text = stringResource(Res.string.register_button_register), // String resource
        onClick = onRegisterClick,
        enabled = termsAcceptedValue // Aktif jika termsAccepted == true
    )

    Spacer(modifier = Modifier.height(dimens.spacingLarge)) // Dimensi tema

    // Divider dan Teks Pemisah
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth().padding(bottom = dimens.paddingLarge), // Dimensi tema
        thickness = dimens.dividerThickness, // Dimensi tema
        color = MaterialTheme.colorScheme.outlineVariant // Warna tema
    )
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(Res.string.register_divider_text), // String resource
            color = MaterialTheme.colorScheme.onSurfaceVariant // Warna tema
        )
    }
    Spacer(modifier = Modifier.height(dimens.spacingLarge)) // Dimensi tema

    // Gunakan Komponen Social Login Buttons yang Baru
    GoogleFacebookLoginRow(
        modifier = Modifier.fillMaxWidth(),
        onGoogleClick = { /* TODO: Implement Google sign up */ },
        onFacebookClick = { /* TODO: Implement Facebook sign up */ }
    )

    Spacer(modifier = Modifier.height(dimens.spacingLarge)) // Dimensi tema
}
