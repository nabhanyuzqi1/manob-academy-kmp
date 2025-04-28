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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
// Import Utilitas Platform & Resource Anda
import com.mnb.manobacademy.util.PlatformType // Sesuaikan path jika perlu
import com.mnb.manobacademy.util.currentPlatform // Sesuaikan path jika perlu
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
 * ... (KDoc lainnya tetap sama) ...
 * @param onForgotPasswordClick Lambda untuk navigasi ke layar lupa kata sandi. // <<< TAMBAHKAN INI
 * ...
 */
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
    onForgotPasswordClick: () -> Unit // <<< TAMBAHKAN PARAMETER INI
) {
    val dimens = MaterialTheme.dimens

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        if (currentPlatform == PlatformType.DESKTOP) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .width(dimens.desktopFormMaxWidth)
                        .fillMaxHeight()
                        .padding(vertical = dimens.paddingLarge),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // --- BAGIAN 1: ATAS ---
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

                    // --- BAGIAN 2: TENGAH ---
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val scrollState = rememberScrollState()
                        Column(
                            modifier = Modifier
                                .verticalScroll(scrollState)
                                .padding(
                                    horizontal = dimens.paddingHuge,
                                    vertical = dimens.formInternalPaddingVertical
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            LoginFormFields(
                                emailValue = email,
                                passwordValue = password,
                                onEmailChange = { email = it },
                                onPasswordChange = { password = it },
                                onLoginClick = onLoginSuccess,
                                onForgotPasswordClick = onForgotPasswordClick // <<< TERUSKAN LAMBDA
                            )
                        }
                    }

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
        } else {
            // --- Layout Mobile ---
            MobileLoginLayout(
                email = email,
                password = password,
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onLoginSuccess = onLoginSuccess,
                onNavigateToRegister = onNavigateToRegister,
                onForgotPasswordClick = onForgotPasswordClick // <<< TERUSKAN LAMBDA
            )
        }
    }
}

@Composable
private fun MobileLoginLayout(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onForgotPasswordClick: () -> Unit // <<< TAMBAHKAN PARAMETER INI
) {
    val dimens = MaterialTheme.dimens

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- BAGIAN 1: ATAS ---
        Spacer(modifier = Modifier.height(dimens.topSpacingMobile))
        Image(
            painterResource(Res.drawable.logo_manob_academy_notext),
            contentDescription = stringResource(Res.string.logo_content_description),
            modifier = Modifier.size(dimens.logoSizeLarge)
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

        // --- BAGIAN 2: TENGAH ---
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .imePadding()
                .padding(
                    horizontal = dimens.paddingHuge,
                    vertical = dimens.formInternalPaddingVertical
                )
        ) {
            LoginFormFields(
                emailValue = email,
                passwordValue = password,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onLoginClick = onLoginSuccess,
                onForgotPasswordClick = onForgotPasswordClick // <<< TERUSKAN LAMBDA
            )
        }

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


@Composable
private fun LoginFormFields(
    emailValue: String,
    passwordValue: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit // <<< TAMBAHKAN PARAMETER INI
) {
    val dimens = MaterialTheme.dimens

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

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        // Gunakan lambda yang diterima dari parameter
        TextButton(onClick = onForgotPasswordClick) { // <<< GUNAKAN LAMBDA DI SINI
            Text(
                stringResource(Res.string.login_forgot_password),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    Spacer(modifier = Modifier.height(dimens.spacingMassive))

    PrimaryActionButton(
        text = stringResource(Res.string.login_button_signin),
        onClick = onLoginClick
    )

    Spacer(modifier = Modifier.height(dimens.spacingExtraLarge))

    HorizontalDivider(
        modifier = Modifier.fillMaxWidth().padding(bottom = dimens.paddingLarge),
        thickness = dimens.dividerThickness,
        color = MaterialTheme.colorScheme.outlineVariant
    )
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(Res.string.login_divider_text),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    Spacer(modifier = Modifier.height(dimens.spacingLarge))

    GoogleFacebookLoginRow(
        modifier = Modifier.fillMaxWidth(),
        onGoogleClick = { /* TODO */ },
        onFacebookClick = { /* TODO */ }
    )

    Spacer(modifier = Modifier.height(dimens.spacingLarge))
}
