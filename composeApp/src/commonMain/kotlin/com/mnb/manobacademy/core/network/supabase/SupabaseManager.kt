package com.mnb.manobacademy.core.network.supabase

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

object SupabaseManager {
    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = SupabaseCredentials.SUPABASE_URL,
            supabaseKey = SupabaseCredentials.SUPABASE_ANON_KEY
        ) {
            install(Postgrest)
            install(Auth)
            install(Storage)
            install(Realtime)
        }
    }
}