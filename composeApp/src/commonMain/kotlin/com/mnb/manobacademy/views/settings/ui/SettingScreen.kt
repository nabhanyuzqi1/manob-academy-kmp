package com.mnb.manobacademy.views.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Support
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Alignment
import manobacademykmp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen() {
    Column {
        TopAppBar(
            title = { Text("Settings") }
        )

        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            item {
                Text(text = "GENERAL", style = MaterialTheme.typography.titleMedium)
            }
            items(generalSettings) { setting ->
                SettingItem(setting.title, setting.icon)
            }

            item {
                Text(text = "FEEDBACK", style = MaterialTheme.typography.titleMedium)
            }
            items(feedbackSettings) { setting ->
                SettingItem(setting.title, setting.icon)
            }
        }
    }
}

@Composable
fun SettingItem(title: String, icon: ImageVector) {
    ListItem(
        modifier = Modifier.fillMaxWidth(),
        text = { Text(title) },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        trailing = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp, // Use appropriate arrow icon
                contentDescription = null
            )
        },
        overlineText = null
    )
}

// Sample Data
data class Setting(val title: String, val icon: ImageVector)

val generalSettings = listOf(
    Setting("Account", Icons.Filled.Person),         // Replace with actual icons
    Setting("Notifications", Icons.Filled.Notifications),
    Setting("Coupons", Icons.Filled.Label),
    Setting("Logout", Icons.Filled.ExitToApp),
    Setting("Delete account", Icons.Filled.Delete)
)

val feedbackSettings = listOf(
    Setting("Report a bug", Icons.Filled.Error),
    Setting("Admin", Icons.Filled.Support)
)

