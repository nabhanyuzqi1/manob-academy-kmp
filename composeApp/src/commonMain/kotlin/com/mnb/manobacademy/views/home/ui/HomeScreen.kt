package com.mnb.manobacademy.views.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mnb.manobacademy.ui.theme.dimens
import com.mnb.manobacademy.views.settings.component.SettingsComponent
import manobacademykmp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.StringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(component: SettingsComponent) {
    val state by component.state.subscribeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = { component.onBackClicked() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(Res.string.settings_back_icon_desc))
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = MaterialTheme.dimens.paddingLarge)
        ) {
            SectionTitle(titleRes = Res.string.settings_general)

            SettingsItem(
                titleRes = Res.string.settings_account,
                onClick = { component.onAccountClicked() }
            )
            SettingsItem(
                titleRes = Res.string.settings_notifications,
                onClick = { component.onNotificationsClicked() }
            )
            SettingsItem(
                titleRes = Res.string.settings_coupons,
                onClick = { component.onCouponsClicked() }
            )
            SettingsItem(
                titleRes = Res.string.settings_logout,
                onClick = { component.onLogoutClicked() }
            )
            SettingsItem(
                titleRes = Res.string.settings_delete_account,
                onClick = { component.onDeleteAccountClicked() }
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacingLarge))

            SectionTitle(titleRes = Res.string.settings_feedback)

            SettingsItem(
                titleRes = Res.string.settings_report_bug,
                onClick = { component.onReportBugClicked() }
            )
            SettingsItem(
                titleRes = Res.string.settings_admin,
                onClick = { component.onAdminClicked() }
            )
        }
    }
}

@Composable
private fun SectionTitle(titleRes: StringResource) {
    Text(
        text = stringResource(titleRes),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = MaterialTheme.dimens.spacingMedium)
    )
}

@Composable
private fun SettingsItem(titleRes: StringResource, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = MaterialTheme.dimens.spacingMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(titleRes),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Normal
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null // Intentional omission of description for arrow
        )
    }
}
