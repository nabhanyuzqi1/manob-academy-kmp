package com.mnb.manobacademy.features.auth.ui // Sesuaikan dengan package Anda

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
// import androidx.compose.foundation.background // Tidak digunakan, bisa dihapus jika tidak perlu
import androidx.compose.foundation.layout.* // <- Pastikan wildcard import ini ada
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.* // <- Pastikan wildcard import ini ada
import androidx.compose.runtime.* // <- Pastikan wildcard import ini ada
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

// Import utilitas platform dan resource Anda
import com.mnb.manobacademy.util.PlatformType // Sesuaikan path jika perlu
import com.mnb.manobacademy.util.currentPlatform // Sesuaikan path jika perlu
import manobacademykmp.composeapp.generated.resources.Res // Sesuaikan path jika perlu
import manobacademykmp.composeapp.generated.resources.ic_facebook // Sesuaikan path jika perlu
import manobacademykmp.composeapp.generated.resources.ic_google // Sesuaikan path jika perlu
import manobacademykmp.composeapp.generated.resources.logo_manob_academy_notext // Sesuaikan path jika perlu
import org.jetbrains.compose.resources.painterResource

/**
 * Composable utama untuk layar Login.
 * Menampilkan UI berbeda berdasarkan platform (Desktop vs Mobile/Lainnya).
 * Menerapkan tata letak 3 bagian: Atas tetap, Tengah scrollable, Bawah tetap.
 * Di Desktop, form dipusatkan secara vertikal dan horizontal.
 *
 * @param onNavigateToRegister Lambda untuk navigasi ke layar registrasi.
 * @param onLoginSuccess Lambda yang dipanggil saat login berhasil.
 */
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    // State untuk menyimpan input email dan password
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // --- Logika Pemisahan UI Berdasarkan Platform ---
    if (currentPlatform == PlatformType.DESKTOP) {
        // --- Layout untuk Platform Desktop ---
        // Box digunakan untuk memusatkan Column utama di tengah layar.
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Column utama untuk konten desktop, dengan lebar tetap.
            Column(
                modifier = Modifier
                    .width(450.dp) // Lebar konten di desktop
                    .fillMaxHeight() // Mengisi tinggi yang tersedia (opsional)
                    .padding(vertical = 16.dp), // Padding vertikal
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- BAGIAN 1: ATAS (Tetap) ---
                Spacer(modifier = Modifier.height(40.dp)) // Jarak dari atas container
                Image(
                    painterResource(Res.drawable.logo_manob_academy_notext),
                    contentDescription = "Logo Manob Academy",
                    modifier = Modifier.width(100.dp).height(100.dp) // Ukuran logo desktop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Manob Academy", style = MaterialTheme.typography.headlineSmall)
                Text("Login to your Account", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(24.dp)) // Jarak ke form

                // --- BAGIAN 2: TENGAH (Wadah untuk Form yang Dipusatkan & Scrollable) ---
                // Column ini mengambil sisa ruang vertikal dan memusatkan isinya.
                Column(
                    modifier = Modifier
                        .weight(1f) // Mengambil sisa ruang vertikal
                        .fillMaxWidth(), // Mengisi lebar parent (450dp)
                    verticalArrangement = Arrangement.Center, // <-- Memusatkan secara vertikal
                    horizontalAlignment = Alignment.CenterHorizontally // <-- Memusatkan secara horizontal
                ) {
                    // Column ini berisi form sebenarnya, ukurannya sesuai konten, dan bisa di-scroll.
                    val scrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            // TIDAK menggunakan weight di sini agar ukurannya sesuai konten
                            .verticalScroll(scrollState) // Membuat konten bisa di-scroll
                            .padding(horizontal = 32.dp, vertical = 16.dp), // Padding internal form
                        horizontalAlignment = Alignment.CenterHorizontally // Pusatkan elemen di dalam form (misal: social buttons)
                    ){
                        // Memanggil komponen yang berisi field input dan tombol form
                        LoginFormFields(
                            emailValue = email,
                            passwordValue = password,
                            onEmailChange = { email = it },
                            onPasswordChange = { password = it },
                            onLoginClick = onLoginSuccess,
                            onForgotPasswordClick = { /* TODO: Implementasikan aksi lupa password */ }
                        )
                    }
                }


                // --- BAGIAN 3: BAWAH (Tetap) ---
                // Row ini akan selalu berada di bawah karena bagian tengah menggunakan weight(1f).
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth() // Mengisi lebar parent (450dp)
                        .padding(horizontal = 16.dp, vertical = 20.dp) // Padding untuk baris bawah
                ) {
                    Text(text = "Don't have an account? ", color = Color.Black)
                    TextButton(onClick = onNavigateToRegister) { // Tombol untuk navigasi
                        Text(text = "Sign up here", color = Color(0xFF1657A6))
                    }
                }
            } // Akhir dari Column 450dp
        } // Akhir dari Box centering
    } else {
        // --- Layout untuk Platform Mobile / Lainnya (Tidak Berubah) ---
        MobileLoginLayout(
            email = email,
            password = password,
            onEmailChange = { email = it },
            onPasswordChange = { password = it },
            onLoginSuccess = onLoginSuccess,
            onNavigateToRegister = onNavigateToRegister
        )
    }
}

/**
 * Composable terpisah untuk tata letak layar login di platform Mobile/Lainnya.
 * Menerapkan struktur 3 bagian yang sama (atas, tengah scroll, bawah).
 */
@Composable
private fun MobileLoginLayout(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    // Column utama mengisi seluruh layar.
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- BAGIAN 1: ATAS (Tetap) ---
        Spacer(modifier = Modifier.height(80.dp)) // Jarak dari atas layar
        Image(
            painterResource(Res.drawable.logo_manob_academy_notext),
            contentDescription = "Logo Manob Academy",
            modifier = Modifier.width(120.dp).height(120.dp) // Ukuran logo mobile
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Manob Academy", style = MaterialTheme.typography.headlineSmall)
        Text("Login to your Account", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(24.dp)) // Jarak ke form

        // --- BAGIAN 2: TENGAH (Scrollable & Weighted) ---
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(1f) // Mengambil sisa ruang vertikal
                .fillMaxWidth() // Mengisi lebar layar
                .verticalScroll(scrollState) // Membuat konten bisa di-scroll
                .imePadding() // Menambahkan padding saat keyboard muncul
                .padding(horizontal = 32.dp, vertical = 16.dp) // Padding internal form
        ) {
            // Memanggil komponen field form
            LoginFormFields(
                emailValue = email,
                passwordValue = password,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onLoginClick = onLoginSuccess,
                onForgotPasswordClick = { /* TODO: Implementasikan aksi lupa password */ }
            )
        }

        // --- BAGIAN 3: BAWAH (Tetap) ---
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth() // Mengisi lebar layar
                .padding(horizontal = 16.dp, vertical = 20.dp) // Padding untuk baris bawah
        ) {
            Text(text = "Don't have an account? ", color = Color.Black)
            TextButton(onClick = onNavigateToRegister) {
                Text(text = "Sign up here", color = Color(0xFF1657A6))
            }
        }
    }
}


/**
 * Composable yang berisi elemen-elemen form login:
 * Input fields (Email, Password), Tombol Forgot Password, Tombol Sign In,
 * Divider, dan Tombol Social Login.
 * Komponen ini digunakan oleh layout Desktop dan Mobile.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LoginFormFields(
    emailValue: String,
    passwordValue: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    val textFieldCornerRadius = 30.dp // Radius sudut untuk text field

    // Email Input Field
    OutlinedTextField(
        value = emailValue,
        onValueChange = onEmailChange,
        label = { Text("Email Address") },
        leadingIcon = { Icon(Icons.Filled.Email, "Email", tint = Color.Gray) },
        shape = RoundedCornerShape(textFieldCornerRadius),
        // Konfigurasi warna untuk tampilan tanpa outline
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            focusedLeadingIconColor = Color.Gray,
            unfocusedLeadingIconColor = Color.Gray,
            // Warna background field
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
        ),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true // Hanya satu baris input
    )
    Spacer(modifier = Modifier.height(16.dp)) // Jarak antar field

    // Password Input Field
    OutlinedTextField(
        value = passwordValue,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        leadingIcon = { Icon(Icons.Filled.Lock, "Password", tint = Color.Gray) },
        shape = RoundedCornerShape(textFieldCornerRadius),
        singleLine = true, // Hanya satu baris input
        visualTransformation = PasswordVisualTransformation(), // Menyembunyikan karakter password
        // Konfigurasi warna untuk tampilan tanpa outline
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            focusedLeadingIconColor = Color.Gray, // Sesuaikan jika perlu
            unfocusedLeadingIconColor = Color.Gray, // Sesuaikan jika perlu
            // Warna background field
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
        ), modifier = Modifier.fillMaxWidth()
    )

    // Tombol Lupa Password (di kanan)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End // Rata kanan
    ) {
        TextButton(onClick = onForgotPasswordClick) {
            Text("Forgot Password?", color = Color(0xFF1657A6))
        }
    }

    Spacer(modifier = Modifier.height(48.dp)) // Jarak sebelum tombol Sign In

    // Tombol Sign In Utama
    Button(
        onClick = onLoginClick,
        shape = RoundedCornerShape(50), // Tombol sangat rounded
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1657A6)), // Warna tombol
        modifier = Modifier.fillMaxWidth().height(50.dp), // Ukuran tombol
    ) {
        Text("Sign In", style = MaterialTheme.typography.bodyLarge, color = Color.White)
    }
    Spacer(modifier = Modifier.height(64.dp)) // Jarak sebelum Divider

    // Divider dan Teks Pemisah
    Divider(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        thickness = 1.dp,
        color = Color.LightGray
    )
    // Teks "Or login with" di tengah
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(text = "Or login with", color = Color.DarkGray)
    }
    Spacer(modifier = Modifier.height(16.dp))

    // Tombol Social Login (Google & Facebook)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center, // Tombol di tengah
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Tombol Google
        Button(
            onClick = { /* TODO: Implement Google login */ },
            shape = RoundedCornerShape(20), // Sudut tombol social
            colors = ButtonDefaults.buttonColors(containerColor = Color.White), // Background putih
            modifier = Modifier.size(50.dp) // Ukuran tombol social
        ) {
            Image(
                painterResource(Res.drawable.ic_google),
                contentDescription = "Login with Google",
                modifier = Modifier.requiredSize(24.dp) // Ukuran ikon
            )
        }
        Spacer(modifier = Modifier.width(16.dp)) // Jarak antar tombol social
        // Tombol Facebook
        Button(
            onClick = { /* TODO: Implement Facebook login */ },
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.size(50.dp)
        ) {
            Image(
                painterResource(Res.drawable.ic_facebook),
                contentDescription = "Login with Facebook",
                modifier = Modifier.requiredSize(24.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp)) // Jarak di bawah tombol social
}

// Catatan Penting: (Sama seperti sebelumnya)
// 1. Pastikan dependensi resource KMP.
// 2. Pastikan path resource dan utilitas platform benar.
// 3. Pastikan MaterialTheme terdefinisi.
// 4. Implementasikan logika TODO.
