package com.mnb.manobacademy.views.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mnb.manobacademy.ui.components.PrimaryActionButton
import com.mnb.manobacademy.ui.theme.dimens
import com.mnb.manobacademy.util.getScreenHeightDp
import com.mnb.manobacademy.views.auth.component.OtpInputFields
import com.mnb.manobacademy.views.auth.component.VerificationCodeComponent
import manobacademykmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationCodeScreen(
    // --- PERBAIKAN: Signature disederhanakan, hanya butuh component ---
    component: VerificationCodeComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.subscribeAsState()
    val dimens = MaterialTheme.dimens
    var otpCode by remember { mutableStateOf("") }
    var isOtpComplete by remember { mutableStateOf(false) }

    val screenHeight = getScreenHeightDp()
    val tallScreenThreshold = 700.dp
    val extraTopPadding = if (screenHeight > tallScreenThreshold) 32.dp else 0.dp
    val expectedOtpLength = 6

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.otp_screen_title)) },
                navigationIcon = {
                    // Aksi langsung memanggil fungsi dari component
                    IconButton(onClick = { component.onBackClicked() }) {
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
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = dimens.paddingHuge)
                    .padding(bottom = dimens.paddingExtraLarge)
            ) {
                if (state.error != null) {
                    Text(
                        text = state.error!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = dimens.paddingMedium)
                    )
                }

                PrimaryActionButton(
                    text = stringResource(Res.string.otp_verify_button),
                    onClick = {
                        if (isOtpComplete && !state.isLoading) {
                            component.onVerifyClicked(otpCode)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isOtpComplete && !state.isLoading,
                    loading = state.isLoading
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimens.paddingHuge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(extraTopPadding))
            Spacer(modifier = Modifier.height(dimens.spacingGiant))

            Text(
                // Data email diambil dari component
                text = stringResource(Res.string.otp_prompt) + (component.emailAddress?.let { "\n$it" } ?: ""),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(dimens.spacingMassive))

            OtpInputFields(
                otpLength = expectedOtpLength,
                onOtpFilled = { code ->
                    otpCode = code
                    isOtpComplete = code.length == expectedOtpLength
                },
                modifier = Modifier.padding(horizontal = dimens.paddingSmall)
            )

            Spacer(modifier = Modifier.height(dimens.spacingLarge))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(Res.string.otp_didnt_receive),
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(
                    // Aksi dan state langsung dari component
                    onClick = { if (!state.isLoading && state.isResendEnabled) component.onResendClicked() },
                    enabled = !state.isLoading && state.isResendEnabled
                ) {
                    Text(
                        text = if (state.resendCooldown > 0)
                            "${stringResource(Res.string.otp_resend_link)} (${state.resendCooldown}s)"
                        else
                            stringResource(Res.string.otp_resend_link),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (state.successMessage != null) {
                Spacer(modifier = Modifier.height(dimens.spacingMedium))
                Text(
                    text = state.successMessage!!,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(dimens.spacingLarge))
        }
    }
}