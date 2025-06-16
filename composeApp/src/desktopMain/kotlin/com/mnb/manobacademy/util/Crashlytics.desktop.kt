package com.mnb.manobacademy.util

/**
 * 'actual' implementation for Desktop.
 * This function prints the crash stack trace to the standard error console.
 */
actual fun logAndReportCrash(throwable: Throwable) {
    println("Unhandled exception caught on Desktop:")
    throwable.printStackTrace()
}
