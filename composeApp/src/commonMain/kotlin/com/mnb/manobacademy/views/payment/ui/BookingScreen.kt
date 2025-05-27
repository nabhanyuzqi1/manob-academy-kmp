package com.mnb.manobacademy.views.payment.ui


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.mnb.manobacademy.ui.theme.dimens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen() {
    val dimens = MaterialTheme.dimens


    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFE3F2FD)) {
        Column(modifier = Modifier.padding(dimens.paddingMedium)) {
            Text(
                text = "Booking Class",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = dimens.spacingMedium)
            )


            // Step Progress
            Row(modifier = Modifier.fillMaxWidth()) {
                StepIndicator(isActive = true, stepText = "Checkout")
                StepIndicator(isActive = false, stepText = "Payment")
                StepIndicator(isActive = false, stepText = "Done")
            }


            Spacer(modifier = Modifier.height(dimens.spacingLarge))


            // Booking Items
            BookingItem(title = "Fotografi Newbie", date = "Senin, 20 April 2025", price = "Rp. 90.000", isSelected = true)
            BookingItem(title = "Fotografi Newbie", date = "Senin, 20 April 2025", price = "Rp. 90.000", isSelected = false)
            BookingItem(title = "Workshop Audio", date = "Senin, 20 April 2025", price = "Rp. 90.000", isSelected = true)
            BookingItem(title = "Fotografi Newbie", date = "Senin, 20 April 2025", price = "Rp. 90.000", isSelected = false)


            Spacer(modifier = Modifier.height(dimens.spacingLarge))


            Text("Subtotal: Rp. 180.000", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(dimens.spacingMedium))


            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Checkout")
            }
        }
    }
}

@Composable
fun StepIndicator(isActive: Boolean, stepText: String) {
    val color = if (isActive) MaterialTheme.colorScheme.primary else Color.Gray
    Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color)
                .border(BorderStroke(2.dp, MaterialTheme.colorScheme.primary), RoundedCornerShape(50))
        )


        Text(text = stepText, style = MaterialTheme.typography.bodySmall, color = color)
    }
}


@Composable
fun BookingItem(title: String, date: String, price: String, isSelected: Boolean) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable { /* Handle click event */ }
            .padding(MaterialTheme.dimens.paddingMedium),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text(text = title, style = MaterialTheme.typography.bodyMedium)
            Text(text = date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Text(text = price, style = MaterialTheme.typography.bodyMedium, textDecoration = TextDecoration.LineThrough)
        }
    }
}