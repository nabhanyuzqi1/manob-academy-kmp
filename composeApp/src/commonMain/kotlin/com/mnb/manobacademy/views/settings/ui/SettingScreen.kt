package com.mnb.manobacademy.views.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mnb.manobacademy.ui.theme.LocalDimens
import com.mnb.manobacademy.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onNavigateBack: () -> Unit) {
    val dimens = LocalDimens.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Settings", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    } // Ensure IconButton is on the right for RTL
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background // Light blue background
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = dimens.paddingMedium),
        ) {
            item {
                SettingsSectionTitle(title = "GENERAL")
            }

            item {
                SettingsItem(title = "Account", onClick = { /* Handle Account Click */ })
            }

            item {
                SettingsItem(title = "Notifications", onClick = { /* Handle Notifications Click */ })
            }

            item {
                SettingsItem(title = "Coupons", onClick = { /* Handle Coupons Click */ })
            }

            item {
                SettingsItem(title = "Logout", onClick = { /* Handle Logout Click */ })
            }

            item {
                SettingsItem(title = "Delete account", onClick = { /* Handle Delete Account Click */ })
            }

            item {
                SettingsSectionTitle(title = "FEEDBACK")
            }

            item {
                SettingsItem(title = "Report a bug", onClick = { /* Handle Report a Bug Click */ })
            }

            item {
                SettingsItem(title = "Admin", onClick = { /* Handle Admin Click */ })
            }
        }
    }
}

@Composable
fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth(),
        textAlign = TextAlign.Start // Ensure title is aligned to the start (right in RTL)
    )
}

@Composable
fun SettingsItem(title: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = MaterialTheme.dimens.spacingMedium),
        shape = RoundedCornerShape(MaterialTheme.dimens.cardCornerRadiusMedium),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = MaterialTheme.dimens.cardElevation
    ) {
        Row(
            modifier = Modifier.padding(MaterialTheme.dimens.paddingMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start // Align items to the start (right in RTL)
        ) {
            val icon = when (title) {
                "Account" -> Icons.Default.Person
                "Notifications" -> Icons.Default.Notifications
                "Coupons" -> Icons.Default.CardGiftcard
                "Logout" -> Icons.Default.Logout
                "Delete account" -> Icons.Default.Delete
                "Report a bug" -> Icons.Default.BugReport
                "Admin" -> Icons.Default.Shield
                else -> Icons.Outlined.Settings // Default icon
            }
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.padding(start = MaterialTheme.dimens.spacingMedium) // Padding on the start (left in RTL)
            )
            Text(text = title, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f).padding(start = MaterialTheme.dimens.spacingMedium), textAlign = TextAlign.Start) // Text aligned to start
        }
    }
}