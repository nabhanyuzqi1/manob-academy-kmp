package com.mnb.manobacademy.core.network.supabase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING") // Supress warning jika perlu
internal actual object SupabaseCredentials {

    // Opsi lain (komentari jika tidak dipakai):
    actual val SUPABASE_URL: String = System.getProperty("supabase.url", "DEFAULT_URL")
    actual val SUPABASE_ANON_KEY: String = System.getProperty("supabase.anon.key", "DEFAULT_KEY")
}
