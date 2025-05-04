package com.mnb.manobacademy.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mnb.manobacademy.features.home.component.HomeComponent
import com.mnb.manobacademy.ui.theme.dimens // Import dimens
import manobacademykmp.composeapp.generated.resources.Res // Import Res
import manobacademykmp.composeapp.generated.resources.app_name // Contoh string title
import manobacademykmp.composeapp.generated.resources.home_logout_button // Import string logout
import org.jetbrains.compose.resources.stringResource

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class) // Untuk Scaffold/TopAppBar
@Composable
fun HomeScreen(component: HomeComponent) {
    val dimens = MaterialTheme.dimens

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.app_name)) }, // Contoh title
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
        // Anda bisa menambahkan bottomBar atau elemen Scaffold lain di sini
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Terapkan padding dari Scaffold
                .padding(dimens.paddingLarge), // Padding tambahan
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Selamat Datang! (Home Screen)")
            Spacer(modifier = Modifier.height(dimens.spacingLarge))
            // Tombol Logout
            Button(onClick = component::onLogoutClicked) { // Panggil fungsi dari komponen
                Text(stringResource(Res.string.home_logout_button)) // Teks tombol
            }
        }
    }
}
