@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.mnb.manobacademy.core.network.supabase

internal expect object SupabaseCredentials {
    val SUPABASE_URL: String
    val SUPABASE_ANON_KEY: String
}
