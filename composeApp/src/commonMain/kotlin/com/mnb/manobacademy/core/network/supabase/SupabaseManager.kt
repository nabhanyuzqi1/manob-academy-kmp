package com.mnb.manobacademy.core.network.supabase

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.Postgrest

// <-- Pastikan ini sama persis


object SupabaseManager { // <-- Pastikan nama object juga benar
    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://roaihvboilwmftccdsvz.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJvYWlodmJvaWx3bWZ0Y2Nkc3Z6Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDUwNTUzNzksImV4cCI6MjA2MDYzMTM3OX0.BmuMLeeEEFIykt0PBN9Q1pl72rlZ1G1bCKUD5F5WbG4"
    ) {
        install(Postgrest)
    }
}

