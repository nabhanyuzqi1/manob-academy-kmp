package com.mnb.manobacademy

import android.app.Application
import com.mnb.manobacademy.util.logAndReportCrash

class ManobAcademyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initGlobalCrashHandler()
    }

    private fun initGlobalCrashHandler() {
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
                // Log and report the crash using our KMP expect/actual function
                logAndReportCrash(throwable)

            // Panggil handler default untuk memastikan aplikasi tetap ditutup seperti biasa
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }
}
