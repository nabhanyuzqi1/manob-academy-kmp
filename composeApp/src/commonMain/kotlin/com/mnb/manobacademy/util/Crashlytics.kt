package com.mnb.manobacademy.util

/**
 * 'expect' function to declare a common API for logging crashes.
 * Each platform (Android, Desktop) will provide its own 'actual' implementation.
 */
expect fun logAndReportCrash(throwable: Throwable)
