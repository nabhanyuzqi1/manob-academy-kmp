package com.mnb.manobacademy.features.auth.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import manobacademykmp.composeapp.generated.resources.Res
import manobacademykmp.composeapp.generated.resources.logo_manob_academy
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class, InternalResourceApi::class)
@Preview
@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit // Callback untuk navigasi setelah splash
){
   // val logo: Painter = painterResource(id = )
    LaunchedEffect (Unit){
        delay(3000)
        onNavigateToLogin() // Panggil navigasi ke Login
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
       Column(
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center,
           modifier = Modifier.fillMaxSize()
       ){
           Image(
               painterResource(Res.drawable.logo_manob_academy),
               contentDescription = null,
               modifier = Modifier.size(200.dp)
           )
       }
    }
}
//@Composable
//fun imageResource(resource: DrawableResource): ImageBitmap {
//
//}