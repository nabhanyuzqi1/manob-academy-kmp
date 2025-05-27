package com.mnb.manobacademy.views.payment.ui // Sesuaikan package Anda

import androidx.compose.foundation.BorderStroke // Mungkin tidak diperlukan lagi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
// import androidx.compose.foundation.lazy.items // Tidak digunakan langsung jika struktur item diubah
import androidx.compose.foundation.shape.RoundedCornerShape // Untuk tombol
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle // Untuk radio button selected
import androidx.compose.material.icons.outlined.RadioButtonUnchecked // Untuk radio button unselected
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip // Mungkin tidak diperlukan lagi
import androidx.compose.ui.graphics.Color // Mungkin tidak diperlukan lagi
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mnb.manobacademy.models.PaymentCategory
import com.mnb.manobacademy.models.PaymentMethod
import com.mnb.manobacademy.ui.theme.AppDimens
import com.mnb.manobacademy.ui.theme.Dimensions
import com.mnb.manobacademy.views.booking.ui.BookingProgressIndicator // Reuse
import com.mnb.manobacademy.views.payment.component.PaymentComponent
import manobacademykmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(component: PaymentComponent) {
    val state by component.state.subscribeAsState()
    val dimens = AppDimens

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.payment_screen_title)) },
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

            BookingProgressIndicator(
                currentStep = 1, // "Payment" adalah step 1 di sini
                steps = listOf(
                    stringResource(Res.string.booking_step_checkout),
                    stringResource(Res.string.booking_step_payment),
                    stringResource(Res.string.booking_step_done)
                ),
                dimens = dimens
            )

            Spacer(modifier = Modifier.height(dimens.paddingLarge))

            Text(
                stringResource(Res.string.payment_select_method_label),
                style = MaterialTheme.typography.titleMedium, // Sesuai UI, ini cukup besar
                fontWeight = FontWeight.Bold, // UI terlihat bold
                modifier = Modifier.padding(bottom = dimens.spacingMedium)
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimens.spacingSmall) // Sedikit jarak antar section
            ) {
                val groupedMethods = state.paymentMethods.groupBy { it.category }

                PaymentCategory.entries.forEach { category ->
                    groupedMethods[category]?.let { methodsInCategory ->
                        item {
                            PaymentCategorySection(
                                category = category,
                                methods = methodsInCategory,
                                selectedMethodId = state.selectedPaymentMethodId,
                                onMethodSelected = component::onPaymentMethodSelected,
                                dimens = dimens
                            )
                        }
                        // Tambahkan sedikit ruang setelah setiap kategori kecuali yang terakhir
                        if (category != PaymentCategory.entries.last()) {
                            item { Spacer(modifier = Modifier.height(dimens.spacingMedium)) }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(dimens.paddingLarge))

            Button(
                onClick = component::onPayNowClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.buttonHeight),
                shape = RoundedCornerShape(dimens.primaryButtonCornerRadius), // Tombol rounded
                enabled = state.selectedPaymentMethodId != null
            ) {
                Text(stringResource(Res.string.payment_pay_now_button))
            }
            Spacer(modifier = Modifier.height(dimens.paddingMedium))
        }
    }
}

@Composable
private fun PaymentCategorySection(
    category: PaymentCategory,
    methods: List<PaymentMethod>,
    selectedMethodId: String?,
    onMethodSelected: (String) -> Unit,
    dimens: Dimensions
) {
    val categoryTitleRes = when (category) {
        PaymentCategory.CREDIT_DEBIT -> Res.string.payment_credit_debit_cards
        PaymentCategory.BANK_TRANSFER -> Res.string.payment_atm_bank_transfer
        PaymentCategory.E_MONEY -> Res.string.payment_e_money
        PaymentCategory.OTC -> Res.string.payment_over_the_counter
        PaymentCategory.OTHER -> Res.string.payment_other_payment
    }

    Column {
        // Baris untuk Judul Kategori dan Grup Logo (jika ada)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(categoryTitleRes),
                style = MaterialTheme.typography.titleSmall, // UI menunjukkan ini lebih kecil dari "Pilih Metode..."
                fontWeight = FontWeight.SemiBold // UI terlihat semi-bold
            )

            // Tampilkan grup logo di sebelah kanan untuk kategori tertentu
            val shouldShowGroupedLogos = category in listOf(
                PaymentCategory.CREDIT_DEBIT,
                PaymentCategory.BANK_TRANSFER,
                PaymentCategory.E_MONEY,
                PaymentCategory.OTC
            )

            if (shouldShowGroupedLogos) {
                Row(horizontalArrangement = Arrangement.spacedBy(dimens.spacingExtraSmall)) {
                    // Ambil URL ikon yang unik dari metode dalam kategori ini
                    // Batasi jumlah logo yang ditampilkan agar tidak terlalu ramai
                    methods.mapNotNull { it.iconUrl }.distinct().take(4).forEach { iconUrl ->
                        AsyncImage(
                            model = iconUrl,
                            contentDescription = stringResource(Res.string.payment_method_icon_desc, category.name),
                            modifier = Modifier.height(dimens.paymentMethodIconHeight), // Misal: 20.dp - 24.dp
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(top = dimens.spacingSmall, bottom = dimens.spacingSmall))

        // Daftar item metode pembayaran yang dapat dipilih di bawah kategori
        methods.forEachIndexed { index, method ->
            PaymentMethodRow(
                methodName = method.name,
                // Untuk QRIS (OTHER), logo ditampilkan di samping nama
                iconUrl = if (category == PaymentCategory.OTHER) method.iconUrl else null,
                isSelected = method.id == selectedMethodId,
                onClick = { onMethodSelected(method.id) },
                dimens = dimens
            )
            // Tambahkan divider antar item, kecuali setelah item terakhir dalam grup
            if (index < methods.size - 1) {
                HorizontalDivider(
                    modifier = Modifier.padding(
                        start = dimens.paddingLarge, // Indentasi divider
                        top = dimens.spacingExtraSmall,
                        bottom = dimens.spacingExtraSmall
                    ),
                    thickness = 0.5.dp, // Divider tipis
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                )
            }
        }
    }
}

@Composable
private fun PaymentMethodRow(
    methodName: String,
    iconUrl: String?, // Hanya untuk kasus khusus seperti QRIS yang logonya di baris item
    isSelected: Boolean,
    onClick: () -> Unit,
    dimens: Dimensions
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = dimens.spacingMedium), // Padding vertikal lebih besar agar mudah diklik
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isSelected) Icons.Filled.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
            contentDescription = stringResource(Res.string.payment_method_selected_desc, methodName),
            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(dimens.paddingLarge) // Ukuran ikon radio button (misal: 24.dp)
        )

        Spacer(modifier = Modifier.width(dimens.spacingMedium)) // Jarak antara radio dan teks

        Text(
            text = methodName,
            style = MaterialTheme.typography.bodyLarge, // Teks nama metode
            modifier = Modifier.weight(1f), // Agar nama metode mengambil sisa ruang
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )

        // Tampilkan logo individual jika ada (untuk kasus seperti QRIS)
        iconUrl?.let {
            Spacer(modifier = Modifier.width(dimens.spacingSmall)) // Jarak sebelum logo individual
            AsyncImage(
                model = it,
                contentDescription = stringResource(Res.string.payment_method_icon_desc, methodName),
                // Ukuran logo QRIS di UI terlihat cukup besar
                modifier = Modifier.height(dimens.paymentMethodIconHeight * 1.5f) // Misal 36.dp, sesuaikan
                    .widthIn(max = dimens.paymentMethodIconMaxWidth), // Misal 60.dp
                contentScale = ContentScale.Fit
            )
        }
    }
}