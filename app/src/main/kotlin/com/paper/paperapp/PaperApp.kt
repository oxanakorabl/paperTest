package com.paper.paperapp

import android.app.Application
import timber.log.Timber

class PaperApp : Application() {

    companion object {
        lateinit var INSTANCE: PaperApp
    }

    override fun onCreate() {
        super.onCreate()

        Timber.e("oxi onCreate")

        INSTANCE = this

        initLogger()
        initCrashlytics()
        //initCalligraphy()
        // .....

    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }


    private fun initCrashlytics() {
        // Set up Crashlytics, disabled for debug builds
//        val crashlyticsKit = Crashlytics.Builder()
//            .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
//            .build()
//
//        val fabric = Fabric.Builder(this)
//            .kits(crashlyticsKit)
//            .debuggable(true)
//            .build()
//        Fabric.with(fabric)
    }

}