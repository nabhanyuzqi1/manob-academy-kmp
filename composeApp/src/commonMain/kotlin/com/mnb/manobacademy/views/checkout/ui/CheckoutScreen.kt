package com.mnb.manobacademy.views.checkout.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mnb.manobacademy.models.BookingItem
import com.mnb.manobacademy.ui.theme.AppDimens
import com.mnb.manobacademy.ui.theme.Dimensions
import com.mnb.manobacademy.views.booking.component.DefaultBookingComponent
import com.mnb.manobacademy.views.booking.ui.BookingProgressIndicator
import com.mnb.manobacademy.views.checkout.component.CheckoutComponent
import manobacademykmp.composeapp.generated.resources.Res
import manobacademykmp.composeapp.generated.resources.booking_step_checkout
import manobacademykmp.composeapp.generated.resources.booking_step_done
import manobacademykmp.composeapp.generated.resources.booking_step_payment
import manobacademykmp.composeapp.generated.resources.checkout_item_ends_on
import manobacademykmp.composeapp.generated.resources.checkout_no_items_in_cart
import manobacademykmp.composeapp.generated.resources.checkout_order_label
import manobacademykmp.composeapp.generated.resources.checkout_order_summary_title
import manobacademykmp.composeapp.generated.resources.checkout_payment_method_button
import manobacademykmp.composeapp.generated.resources.checkout_screen_title
import manobacademykmp.composeapp.generated.resources.checkout_total_label
import manobacademykmp.composeapp.generated.resources.otp_back_button_desc
import org.jetbrains.compose.resources.stringResource

// Untuk AsyncImage, tambahkan dependensi coil-compose jika belum:
// implementation("io.coil-kt:coil-compose:2.6.0") // atau versi terbaru
// import coil.compose.AsyncImage // Aktifkan jika menggunakan Coil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(component: CheckoutComponent) {
    val state by component.state.subscribeAsState()
    val dimens = AppDimens

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.checkout_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = component::onBackClicked) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.otp_back_button_desc)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = dimens.paddingMedium)
        ) {
            Spacer(modifier = Modifier.height(dimens.paddingMedium))

            BookingProgressIndicator( // Reuse dari BookingScreen
                currentStep = 0, // "Checkout" adalah step 0 di sini
                steps = listOf(
                    stringResource(Res.string.booking_step_checkout),
                    stringResource(Res.string.booking_step_payment),
                    stringResource(Res.string.booking_step_done)
                ),
                dimens = dimens
            )

            Spacer(modifier = Modifier.height(dimens.paddingLarge))

            if (state.itemsToCheckout.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        stringResource(Res.string.checkout_no_items_in_cart),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dimens.spacingMedium)
                ) {
                    items(state.itemsToCheckout, key = { it.id }) { item ->
                        CheckoutItemCard(item = item, dimens = dimens)
                    }
                }
            }


            Spacer(modifier = Modifier.height(dimens.paddingMedium))

            CheckoutOrderSummary(
                items = state.itemsToCheckout, // Kirim semua item untuk kalkulasi di Composable jika perlu
                totalAmount = state.totalAmount,
                dimens = dimens
            )

            Spacer(modifier = Modifier.height(dimens.paddingLarge))

            Button(
                onClick = component::onNavigateToPaymentMethod,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.buttonHeight),
                shape = RoundedCornerShape(dimens.primaryButtonCornerRadius), // Gunakan corner radius yang lebih besar
                enabled = state.itemsToCheckout.isNotEmpty()
            ) {
                Text(stringResource(Res.string.checkout_payment_method_button))
            }
            Spacer(modifier = Modifier.height(dimens.paddingMedium))
        }
    }
}

@Composable
private fun CheckoutItemCard(item: BookingItem, dimens: Dimensions) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimens.cardCornerRadiusMedium),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)), // Warna latar sesuai UI
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .padding(dimens.paddingMedium)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder Gambar (jika ada URL gambar di BookingItem)
            // Box(
            //     modifier = Modifier
            //         .size(dimens.bookingItemImageSize) // Misal 72.dp
            //         .clip(RoundedCornerShape(dimens.cardCornerRadiusSmall))
            //         .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)),
            //     contentAlignment = Alignment.Center
            // ) {
            //     if (item.imageUrl != null) {
            //         AsyncImage(
            //             model = item.imageUrl,
            //             contentDescription = stringResource(Res.string.booking_item_image_desc),
            //             contentScale = ContentScale.Crop,
            //             modifier = Modifier.fillMaxSize(),
            //             // onError = // Handle error
            //             // placeholder = // Placeholder
            //         )
            //     } else {
            //         Icon(
            //             Icons.Filled.PhotoCamera,
            //             contentDescription = stringResource(Res.string.booking_item_image_desc),
            //             modifier = Modifier.size(dimens.paddingHuge),
            //             tint = MaterialTheme.colorScheme.onSecondaryContainer
            //         )
            //     }
            // }
            // Spacer(modifier = Modifier.width(dimens.spacingMedium))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium, // Lebih besar dari di BookingScreen
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(dimens.spacingExtraSmall))
                Text(
                    // Asumsi schedule di BookingItem berisi tanggal akhir
                    text = stringResource(Res.string.checkout_item_ends_on, item.schedule),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(dimens.spacingLarge))
            Text(
                text = item.priceFormatted, // Dari BookingItem
                style = MaterialTheme.typography.titleMedium, // Lebih besar
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface // Warna harga sesuai UI
            )
        }
    }
}

@Composable
private fun CheckoutOrderSummary(items: List<BookingItem>, totalAmount: Double, dimens: Dimensions) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimens.cardCornerRadiusMedium),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = dimens.cardElevation)
    ) {
        Column(modifier = Modifier.padding(dimens.paddingMedium)) {
            Text(
                stringResource(Res.string.checkout_order_summary_title),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = dimens.spacingSmall)
            )
            HorizontalDivider()
            Spacer(modifier = Modifier.height(dimens.spacingMedium))

            // UI menampilkan "Order" Rp.90.000 dua kali, lalu "Total" Rp.90.000
            // Ini mungkin berarti jika ada 2 item @90rb, totalnya 180rb.
            // Atau jika hanya 1 item, "Order" dan "Total" sama.
            // Kita akan menampilkan setiap item atau subtotal.
            // Untuk sederhana, kita tampilkan subtotal dari item yang ada.

            // Jika ingin menampilkan setiap item di summary:
            // items.forEach { item ->
            //     Row(
            //         modifier = Modifier.fillMaxWidth(),
            //         horizontalArrangement = Arrangement.SpaceBetween
            //     ) {
            //         Text(item.title, style = MaterialTheme.typography.bodyMedium)
            //         Text(item.priceFormatted, style = MaterialTheme.typography.bodyMedium)
            //     }
            //     Spacer(modifier = Modifier.height(dimens.spacingSmall))
            // }

            // Atau, jika "Order" adalah subtotal sebelum diskon/pajak (jika ada)
            // Untuk UI yang diberikan, "Order" tampaknya adalah total sebelum ada modifikasi.
            // Kita akan anggap "Order" adalah totalAmount untuk saat ini.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(Res.string.checkout_order_label), style = MaterialTheme.typography.bodyMedium)
                Text(DefaultBookingComponent.formatPrice(totalAmount), style = MaterialTheme.typography.bodyMedium)
            }
            // Jika ada beberapa "Order" seperti di UI, Anda bisa ulangi baris di atas
            // atau jika itu adalah item individual, loop seperti di atas.
            // UI menunjukkan dua baris "Order" dengan harga yang sama. Ini bisa jadi placeholder
            // atau representasi dari dua item yang sama.
            // Jika `items` memiliki lebih dari satu, Anda bisa menampilkan "Order" untuk subtotal
            // dan kemudian "Total". Untuk kesederhanaan, kita tampilkan satu "Order" yang merupakan total.

            Spacer(modifier = Modifier.height(dimens.spacingMedium))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(dimens.spacingSmall))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(Res.string.checkout_total_label),
                    style = MaterialTheme.typography.titleMedium, // Lebih besar
                    fontWeight = FontWeight.Bold
                )
                Text(
                    DefaultBookingComponent.formatPrice(totalAmount),
                    style = MaterialTheme.typography.titleMedium, // Lebih besar
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary // Warna total
                )
            }
        }
    }
}