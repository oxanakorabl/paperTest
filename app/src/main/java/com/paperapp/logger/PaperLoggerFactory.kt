package com.paperapp.logger

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.paperapp.BuildConfig
import timber.log.Timber

object PaperLoggerFactory {
    fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }

    /* ToDo: replace string tag with enum tag */
    fun getLogger(tag: String): PaperLogger {
        return PaperLogger(tag)
    }
}