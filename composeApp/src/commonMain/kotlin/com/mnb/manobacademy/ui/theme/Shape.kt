package com.mnb.manobacademy.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// --- 4. Definisi Shapes ---
val Shapes = Shapes(
    // Bentuk sudut kecil (misal: Chip, TextField kecil)
    small = RoundedCornerShape(8.dp), // Sedikit lebih bulat dari default (4.dp)
    // Bentuk sudut medium (misal: Card, Button, TextField standar)
    medium = RoundedCornerShape(12.dp), // Lebih bulat dari default (8.dp)
    // Bentuk sudut besar (misal: Dialog, BottomSheet)
    large = RoundedCornerShape(16.dp), // Lebih bulat dari default (12.dp)
    // Anda bisa menambahkan extraSmall atau extraLarge jika perlu
    extraLarge = RoundedCornerShape(30.dp)
)
