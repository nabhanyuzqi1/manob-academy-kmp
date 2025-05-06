package com.mnb.manobacademy.core.network.supabase

import com.mnb.manobacademy.BuildConfig


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual object SupabaseCredentials {
    actual val SUPABASE_URL: String = BuildConfig.SUPABASE_URL
    actual val SUPABASE_ANON_KEY: String = BuildConfig.SUPABASE_ANON_KEY
}
