package com.mnb.manobacademy.util

import android.util.Log

/**
 * 'actual' implementation for Android.
 * This function logs the crash to Android's Logcat.
 * In a real-world app, you would integrate a crash reporting service like
 * Firebase Crashlytics or Sentry here.
 */
actual fun logAndReportCrash(throwable: Throwable) {
    // Log the exception to Logcat with a custom tag
    Log.e("ManobAcademyCrash", "Unhandled exception caught", throwable)

    // TODO: Tambahkan pemanggilan ke layanan crash reporting di sini
    // Contoh: Firebase.crashlytics.recordException(throwable)
}
