package com.mnb.manobacademy.views.booking.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mnb.manobacademy.models.BookingItem
import com.mnb.manobacademy.ui.theme.AppDimens
import com.mnb.manobacademy.ui.theme.Dimensions
import com.mnb.manobacademy.views.home.ui.HomeBottomNavigation
import com.mnb.manobacademy.views.booking.component.BookingComponent
import com.mnb.manobacademy.views.booking.component.DefaultBookingComponent
import manobacademykmp.composeapp.generated.resources.Res
import manobacademykmp.composeapp.generated.resources.booking_checkout_button
import manobacademykmp.composeapp.generated.resources.booking_favorite_icon_desc
import manobacademykmp.composeapp.generated.resources.booking_item_image_desc
import manobacademykmp.composeapp.generated.resources.booking_progress_completed_desc
import manobacademykmp.composeapp.generated.resources.booking_screen_title
import manobacademykmp.composeapp.generated.resources.booking_step_checkout
import manobacademykmp.composeapp.generated.resources.booking_step_done
import manobacademykmp.composeapp.generated.resources.booking_step_payment
import manobacademykmp.composeapp.generated.resources.booking_subtotal_label
import manobacademykmp.composeapp.generated.resources.otp_back_button_desc
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(component: BookingComponent) {
    val state by component.state.subscribeAsState()
    val dimens = AppDimens // Gunakan AppDimens

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.booking_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = component::onBackClicked) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.otp_back_button_desc) // Reuse or new
                        )
                    }
                },
                actions = {
                    IconButton(onClick = component::onFavoriteClicked) {
                        Icon(
                            imageVector = if (state.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = stringResource(Res.string.booking_favorite_icon_desc),
                            tint = if (state.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface // Atau backgroundLight/Dark
                )
            )
        },
        bottomBar = {
            // Gunakan HomeBottomNavigation yang sudah ada dari HomeScreen.kt
            // Pastikan HomeBottomNavigation di HomeScreen.kt public atau pindahkan ke file common
            HomeBottomNavigation(
                currentRoute = state.currentBottomNavRoute,
                onItemSelected = component::onBottomNavItemSelected,
                // Kirim daftar item navigasi jika HomeBottomNavigation membutuhkannya
                // navItems = BottomNavItem.allItems() // Jika HomeBottomNavigation menerimanya
            )
        },
        containerColor = MaterialTheme.colorScheme.background // backgroundLight/Dark
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Padding dari Scaffold
                .padding(horizontal = dimens.paddingMedium) // Padding horizontal utama untuk konten
        ) {
            Spacer(modifier = Modifier.height(dimens.paddingMedium))

            BookingProgressIndicator(
                currentStep = state.currentStep,
                steps = listOf(
                    stringResource(Res.string.booking_step_checkout),
                    stringResource(Res.string.booking_step_payment),
                    stringResource(Res.string.booking_step_done)
                ),
                dimens = dimens
            )

            Spacer(modifier = Modifier.height(dimens.paddingLarge))

            if (state.bookingItems.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Keranjang Anda kosong.", // Tambahkan ke strings.xml jika perlu
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dimens.spacingMedium)
                ) {
                    items(state.bookingItems, key = { it.id }) { item ->
                        BookingListItem(
                            item = item,
                            onCheckedChanged = { isChecked ->
                                component.onItemCheckedChanged(item.id, isChecked)
                            },
                            dimens = dimens
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(dimens.paddingMedium))

            BookingSummary(
                subtotal = state.subtotal,
                onCheckoutClicked = component::onCheckoutClicked,
                dimens = dimens,
                isEnabled = state.bookingItems.any { it.isSelected } // Tombol checkout aktif jika ada item terpilih
            )
            Spacer(modifier = Modifier.height(dimens.paddingMedium)) // Jarak di bawah tombol
        }
    }
}

@Composable
fun BookingProgressIndicator(
    currentStep: Int,
    steps: List<String>,
    dimens: Dimensions,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Ini akan memberi ruang yang sama
    ) {
        steps.forEachIndexed { index, stepName ->
            val isCompleted = index < currentStep
            val isActive = index == currentStep

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f) // Setiap step mengambil bobot yang sama
            ) {
                Box(
                    modifier = Modifier
                        .size(dimens.progressIndicatorSize) // Gunakan dimensi dari Dimens.kt jika ada, atau 24.dp
                        .clip(CircleShape)
                        .background(
                            when {
                                isActive -> MaterialTheme.colorScheme.primary
                                isCompleted -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.surfaceVariant // Warna untuk yang belum aktif
                            }
                        )
                        .border(
                            BorderStroke(
                                dimens.progressIndicatorStroke, // Gunakan dimensi, atau 2.dp
                                if (isActive || isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                            ),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isCompleted) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = stringResource(Res.string.booking_progress_completed_desc),
                            tint = MaterialTheme.colorScheme.onPrimary, // Warna ikon centang
                            modifier = Modifier.size(dimens.progressIndicatorSize * 0.6f) // Ukuran ikon lebih kecil dari lingkaran
                        )
                    } else if (isActive) {
                        // Titik di tengah untuk step aktif
                        Box(
                            modifier = Modifier
                                .size(dimens.progressIndicatorDotSize) // Gunakan dimensi, atau 8.dp
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.onPrimary)
                        )
                    }
                    // Tidak ada apa-apa untuk step yang belum aktif (hanya border)
                }
                Spacer(modifier = Modifier.height(dimens.spacingSmall))
                Text(
                    text = stepName,
                    style = MaterialTheme.typography.labelMedium, // Atau labelSmall
                    color = if (isActive || isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
                )
            }

            // Garis penghubung antar step
            if (index < steps.size - 1) {
                HorizontalDivider(
                    modifier = Modifier
                        .weight(0.5f) // Bobot untuk garis, bisa disesuaikan
                        .padding(bottom = dimens.paddingMedium + dimens.spacingSmall), // Sejajarkan dengan lingkaran dari bawah
                    thickness = dimens.progressIndicatorStroke, // Gunakan dimensi atau 2.dp
                    color = if (isCompleted || (isActive && index < currentStep)) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}


@Composable
fun BookingListItem(
    item: BookingItem,
    onCheckedChanged: (Boolean) -> Unit,
    dimens: Dimensions
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimens.cardCornerRadiusMedium), // Misal: 12.dp
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(dimens.paddingMedium) // Misal: 16.dp
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder Gambar
            Box(
                modifier = Modifier
                    .size(dimens.instructorAvatarSize) // Misal: 60.dp (sesuaikan dengan UI)
                    .clip(RoundedCornerShape(dimens.cardCornerRadiusMedium)) // Misal: 4.dp
                    .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.PhotoCamera,
                    contentDescription = stringResource(Res.string.booking_item_image_desc),
                    modifier = Modifier.size(dimens.paddingLarge), // Misal: 24.dp
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
                // TODO: Ganti dengan AsyncImage jika URL tersedia
                // Contoh:
                // if (item.imageUrl != null) {
                //     AsyncImage(
                //         model = item.imageUrl,
                //         contentDescription = stringResource(Res.string.booking_item_image_desc),
                //         contentScale = ContentScale.Crop,
                //         modifier = Modifier.fillMaxSize()
                //     )
                // }
            }

            Spacer(modifier = Modifier.width(dimens.spacingMedium)) // Misal: 8.dp

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(dimens.spacingExtraSmall)) // Misal: 2.dp
                Text(
                    text = item.schedule,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(dimens.spacingSmall)) // Misal: 4.dp
                Text(
                    text = item.priceFormatted,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(dimens.spacingSmall))

            Checkbox(
                checked = item.isSelected,
                onCheckedChange = onCheckedChanged,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary // Warna centang
                ),
                modifier = Modifier.size(dimens.socialButtonSize) // Agar area klik lebih besar
            )
        }
    }
}

@Composable
fun BookingSummary(
    subtotal: Double,
    onCheckoutClicked: () -> Unit,
    dimens: Dimensions,
    isEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimens.paddingSmall), // Jarak vertikal di sekitar summary
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(Res.string.booking_subtotal_label),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = DefaultBookingComponent.formatPrice(subtotal),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Button(
            onClick = onCheckoutClicked,
            shape = RoundedCornerShape(dimens.primaryButtonCornerRadius), // Misal: 8.dp
            modifier = Modifier.height(dimens.buttonHeight), // Misal: 48.dp
            enabled = isEnabled, // Aktifkan tombol berdasarkan kondisi
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            )
        ) {
            Text(stringResource(Res.string.booking_checkout_button))
        }
    }
}