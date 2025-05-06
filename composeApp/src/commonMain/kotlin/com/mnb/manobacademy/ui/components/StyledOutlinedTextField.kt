package com.mnb.manobacademy.ui.components // <- Sesuaikan dengan package Anda

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
// Hapus import dp jika tidak digunakan lagi secara eksplisit
// import androidx.compose.ui.unit.dp
import com.mnb.manobacademy.ui.theme.dimens // Import AppDimens untuk akses mudah

// Hapus variabel DefaultTextFieldCornerRadius yang salah didefinisikan
// private val DefaultTextFieldCornerRadius = MaterialTheme.dimens

/**
 * Composable kustom yang membungkus OutlinedTextField dengan gaya spesifik:
 * - Sudut bulat (mengambil dari AppDimens.current.textFieldCornerRadius secara default).
 * - Menggunakan warna border, label, ikon, kursor, dan container dari MaterialTheme.
 * - Biasanya single line.
 *
 * Dapat digunakan kembali di seluruh aplikasi untuk tampilan text field yang konsisten.
 * PENTING: Harus digunakan di dalam lingkup `AppTheme` yang menyediakan `LocalDimens`.
 *
 * @param value Teks yang akan ditampilkan.
 * @param onValueChange Lambda yang dipanggil saat nilai berubah.
 * @param modifier Modifier yang akan diterapkan pada text field.
 * @param enabled Mengontrol status aktif text field.
 * @param readOnly Mengontrol apakah text field hanya bisa dibaca.
 * @param label Composable opsional untuk menampilkan label.
 * @param placeholder Composable opsional untuk menampilkan placeholder.
 * @param leadingIcon Composable opsional untuk menampilkan ikon di awal.
 * @param trailingIcon Composable opsional untuk menampilkan ikon di akhir.
 * @param isError Menunjukkan jika input saat ini dianggap error.
 * @param visualTransformation Mengubah representasi visual input (misalnya, untuk password).
 * @param keyboardOptions Opsi konfigurasi software keyboard.
 * @param keyboardActions Aksi yang dilakukan saat tombol aksi IME ditekan.
 * @param singleLine Jika true, input akan menjadi satu baris horizontal.
 * @param shape Bentuk outline text field. Default menggunakan RoundedCornerShape dengan radius dari AppDimens.
 * @param colors Warna yang digunakan untuk text field dalam status berbeda. Default menggunakan konfigurasi warna dari tema.
 */
@Composable
fun StyledOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    // Ambil nilai corner radius langsung dari AppDimens saat composable dijalankan
    shape: Shape = RoundedCornerShape(MaterialTheme.dimens.textFieldCornerRadius),
    // Gunakan warna dari MaterialTheme.colorScheme
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        // Warna Border: Gunakan outline/outlineVariant dari tema
        focusedBorderColor = MaterialTheme.colorScheme.primary, // Atau MaterialTheme.colorScheme.outline jika ingin lebih subtle
        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
        errorBorderColor = MaterialTheme.colorScheme.error, // Warna error dari tema
        disabledBorderColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), // Border disable

        // Warna Kursor: Gunakan primary dari tema
        cursorColor = MaterialTheme.colorScheme.primary,

        // Warna Label: Primary saat fokus, onSurfaceVariant saat tidak fokus
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        errorLabelColor = MaterialTheme.colorScheme.error, // Warna error dari tema
        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f), // Label disable

        // Warna Ikon: Primary saat fokus, onSurfaceVariant saat tidak fokus
        focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        errorLeadingIconColor = MaterialTheme.colorScheme.error, // Warna error dari tema
        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f), // Ikon disable
        focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        errorTrailingIconColor = MaterialTheme.colorScheme.error,
        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),

        // Warna Container (Background): Gunakan surface dari tema
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
        disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f), // Background disable
        errorContainerColor = MaterialTheme.colorScheme.surface, // Atau errorContainer jika ingin beda

        // Warna Teks: Otomatis menggunakan onSurface/onError dari tema (biasanya tidak perlu di-override)
        // focusedTextColor = MaterialTheme.colorScheme.onSurface,
        // unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        // disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled), // Material 2 style
        // errorTextColor = MaterialTheme.colorScheme.onError, // Jika container error != surface

        // Warna Placeholder: Gunakan onSurfaceVariant
        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
        errorPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant // Atau onError jika container error != surface
    )
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        shape = shape, // Teruskan shape yang sudah benar
        colors = colors // Teruskan colors yang sudah dikonfigurasi
    )
}
