package com.mnb.manobacademy.features.auth.ui.components // <- Sesuaikan package

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import com.mnb.manobacademy.ui.theme.dimens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Composable untuk input field kode OTP.
 *
 * @param otpLength Jumlah digit OTP.
 * @param onOtpFilled Lambda yang dipanggil ketika semua digit telah diisi.
 */
@Composable
fun OtpInputFields(
    modifier: Modifier = Modifier,
    otpLength: Int = 6,
    onOtpFilled: (String) -> Unit
) {
    val dimens = MaterialTheme.dimens
    var otpValue by remember { mutableStateOf(List(otpLength) { "" }) }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Fokus ke field pertama saat komponen muncul
        focusRequesters[0].requestFocus()
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween // Atau Arrangement.spacedBy(dimens.spacingMedium)
    ) {
        otpValue.forEachIndexed { index, value ->
            OtpDigitInput(
                value = value,
                onValueChange = { newValue ->
                    val currentOtp = otpValue.toMutableList()
                    if (newValue.length <= 1) { // Hanya izinkan satu digit per field
                        currentOtp[index] = newValue
                        otpValue = currentOtp

                        // Pindah fokus ke field berikutnya jika digit dimasukkan
                        if (newValue.isNotEmpty() && index < otpLength - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }

                        // Cek apakah OTP sudah lengkap
                        if (otpValue.all { it.isNotEmpty() }) {
                            onOtpFilled(otpValue.joinToString(""))
                        }
                    } else if (newValue.length > 1 && index == 0) {
                        // Handle paste (basic implementation)
                        val pastedCode = newValue.take(otpLength)
                        otpValue = pastedCode.padEnd(otpLength, ' ').map { it.toString().trim() }
                        if (otpValue.all { it.isNotEmpty() }) {
                            onOtpFilled(otpValue.joinToString(""))
                        }
                        // Pindahkan fokus ke akhir setelah paste
                        coroutineScope.launch {
                            delay(50) // Sedikit delay agar state terupdate
                            focusRequesters.getOrNull(pastedCode.length - 1)?.requestFocus()
                        }
                    }
                },
                focusRequester = focusRequesters[index],
                modifier = Modifier
                    .weight(1f) // Agar semua field memiliki lebar sama
                    .aspectRatio(1f) // Membuat field menjadi kotak (opsional)
                    .onKeyEvent { keyEvent ->
                        // Handle backspace untuk pindah ke field sebelumnya
                        if (keyEvent.type == KeyEventType.KeyUp &&
                            keyEvent.key == Key.Backspace &&
                            otpValue[index].isEmpty() &&
                            index > 0
                        ) {
                            focusRequesters[index - 1].requestFocus()
                            true // Event handled
                        } else {
                            false // Event not handled
                        }
                    }
            )
            // Tambahkan Spacer jika tidak menggunakan Arrangement.SpaceBetween
            // if (index < otpLength - 1) {
            //     Spacer(modifier = Modifier.width(dimens.spacingMedium))
            // }
        }
    }
}

@Composable
private fun OtpDigitInput(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    val dimens = MaterialTheme.dimens
    BasicTextField(
        value = TextFieldValue(value, selection = TextRange(value.length)), // Selalu tempatkan kursor di akhir
        onValueChange = { textFieldValue ->
            // Hanya ambil teks baru, abaikan perubahan kursor/seleksi
            if (textFieldValue.text != value) {
                onValueChange(textFieldValue.text.filter { it.isDigit() }) // Hanya izinkan digit
            }
        },
        modifier = modifier.focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Next // Atau Done jika field terakhir
        ),
        singleLine = true,
        maxLines = 1,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .size(dimens.socialButtonSize) // Ukuran field (sesuaikan)
                    .border(
                        width = dimens.dividerThickness,
                        color = if (value.isNotEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                        shape = MaterialTheme.shapes.medium // Atau RoundedCornerShape(dimens.paddingMedium)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineSmall, // Sesuaikan style
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface // Warna teks
                )
                // Tempatkan innerTextField di sini jika ingin kursor terlihat (BasicTextField tidak menampilkannya secara default)
                // innerTextField()
            }
        }
    )
}
