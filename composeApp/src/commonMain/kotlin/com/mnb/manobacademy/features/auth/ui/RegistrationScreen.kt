// --- File: composeApp/src/commonMain/kotlin/features/auth/ui/RegistrationScreen.kt ---
package com.mnb.manobacademy.features.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.mnb.manobacademy.util.PlatformType
import com.mnb.manobacademy.util.currentPlatform

@Composable
fun RegistrationScreen(
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Contoh: Ubah layout berdasarkan platform
    if (currentPlatform == PlatformType.DESKTOP) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.width(400.dp).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                RegistrationFormComponent(
                    fullName, email, password,
                    { fullName = it }, { email = it }, { password = it },
                    onRegisterSuccess, onNavigateToLogin
                )
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            RegistrationFormComponent(
                fullName, email, password,
                { fullName = it }, { email = it }, { password = it },
                onRegisterSuccess, onNavigateToLogin
            )
        }
    }
}

// Composable terpisah untuk form registrasi
@Composable
private fun RegistrationFormComponent(
    fullNameValue: String,
    emailValue: String,
    passwordValue: String,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Text("Daftar Akun Baru", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(24.dp))

    OutlinedTextField(
        value = fullNameValue,
        onValueChange = onFullNameChange,
        label = { Text("Nama Lengkap") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = emailValue,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = passwordValue,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        visualTransformation = PasswordVisualTransformation()
    )
    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = onRegisterClick,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text("Daftar")
    }
    Spacer(modifier = Modifier.height(16.dp))

    TextButton(onClick = onLoginClick) {
        Text("Sudah punya akun? Login di sini")
    }
}