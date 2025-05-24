
package com.mnb.manobacademy.views.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mnb.manobacademy.ui.theme.dimens
import com.mnb.manobacademy.ui.theme.onPrimaryLight
import com.mnb.manobacademy.ui.theme.primaryLight
import manobacademykmp.composeapp.generated.resources.Res
import manobacademykmp.composeapp.generated.resources.edit_profile_title
import manobacademykmp.composeapp.generated.resources.home_logout_button
import manobacademykmp.composeapp.generated.resources.login_email_label
import manobacademykmp.composeapp.generated.resources.login_password_label
import manobacademykmp.composeapp.generated.resources.otp_back_button_desc
import manobacademykmp.composeapp.generated.resources.register_fullname_label
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onBack: () -> Unit = {},
    onSave: () -> Unit = {}
) {
    val dimens = MaterialTheme.dimens

    var name by remember { mutableStateOf("Dryx Siregar") }
    var username by remember { mutableStateOf("dryxsiregar") }
    var gender by remember { mutableStateOf("Male") }
    var expanded by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("dryxsiregar12345") }
    var email by remember { mutableStateOf("dryxsiregar@gmail.com") }

    val genderOptions = listOf("Male", "Female", "Other")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = dimens.paddingLarge)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start // Updated to start align
        ) {
            Spacer(Modifier.height(dimens.spacingLarge))

            // Name Field
            EditFieldLabel("Name")
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Spacer(Modifier.height(dimens.spacingLarge))

            // Username Field
            EditFieldLabel("Username")
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(dimens.spacingLarge))

            // Gender Dropdown
            EditFieldLabel("Gander") // Intentional typo
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = gender,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    genderOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                gender = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(dimens.spacingLarge))

            // Password Field
            EditFieldLabel("Password")
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(dimens.spacingLarge))

            // Email Field
            EditFieldLabel("Email")
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(40.dp))

            // Save Button
            Button(
                onClick = { onSave() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary) // Updated color
            ) {
                Text("Save", color = MaterialTheme.colorScheme.onPrimary)
            }
            Spacer(Modifier.height(dimens.spacingLarge))
        }
    }
}

@Composable
private fun EditFieldLabel(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier
            .padding(bottom = 8.dp, top = 16.dp)
    )
}

