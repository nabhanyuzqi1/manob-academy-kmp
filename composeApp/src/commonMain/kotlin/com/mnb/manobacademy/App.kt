package com.mnb.manobacademy

import io.github.jan.supabase.createSupabaseClient

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable


@Composable
@Preview
fun App() {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://roaihvboilwmftccdsvz.supabase.co", // Replace with your Supabase URL
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJvYWlodmJvaWx3bWZ0Y2Nkc3Z6Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDUwNTUzNzksImV4cCI6MjA2MDYzMTM3OX0.BmuMLeeEEFIykt0PBN9Q1pl72rlZ1G1bCKUD5F5WbG4"  // Replace with your Supabase anon key
    ) {
        install(Postgrest)
    }

    var message by remember { mutableStateOf("") }

    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val data: List<TestTable> = supabase.from("test").select().decodeList()
                        println(data)
                        message = "Data fetched successfully"
                        println("Test connection to Supabase is successful!")
                        if (data.isNotEmpty()) {
                            message = data.first().msg ?: "No message found"
                        } else {
                            message = "No data found in the table"
                        }
                    } catch (e: Exception) {
                        message = "Error: ${e.message}"
                    }
                }
            }) {
                Text("Fetch Data")
            }
            Text(message)
        }
    }
}

@Serializable
data class TestTable(val id: String, val msg: String? = null)

@Preview
@Composable
fun AppPreview() {
    App()
}

