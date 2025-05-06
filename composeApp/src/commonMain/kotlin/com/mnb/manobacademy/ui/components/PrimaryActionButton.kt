package com.mnb.manobacademy.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mnb.manobacademy.ui.theme.dimens

/**
 * Composable modular untuk tombol aksi utama (seperti Sign In, Register, dll.).
 * Menggunakan warna primary dari tema dan bentuk sudut yang sangat bulat.
 *
 * @param text Teks yang ditampilkan di tombol.
 * @param onClick Lambda yang dipanggil saat tombol diklik.
 * @param modifier Modifier untuk kustomisasi tata letak. Default mengisi lebar dan tinggi 50.dp.
 * @param enabled Mengontrol status aktif tombol. Default true.
 * @param shape Bentuk tombol. Default sangat bulat (50.dp).
 */
@Composable
fun PrimaryActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(MaterialTheme.dimens.buttonHeight), // Ukuran default tombol
    enabled: Boolean = true,
    shape: RoundedCornerShape = RoundedCornerShape(MaterialTheme.dimens.primaryButtonCornerRadius) // Bentuk default tombol
) {
    Button(
        onClick = onClick,
        shape = shape,
        enabled = enabled,
        // Gunakan warna primary dan onPrimary dari tema
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            // Anda bisa menambahkan warna disable di sini jika perlu
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
        ),
        modifier = modifier, // Terapkan modifier yang diberikan
    ) {
        // Gunakan typography dari tema
        Text(text, style = MaterialTheme.typography.bodyLarge) // Warna otomatis dari contentColor tombol
    }
}
