package com.paperapp

import android.app.Application
import com.paperapp.interactors.BranchInteractor
import com.paperapp.logger.PaperLoggerFactory
import com.paperapp.interactors.VkInteractor

class PaperApplication : Application() {
    companion object {
        lateinit var INSTANCE: PaperApplication
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        PaperLoggerFactory.initLogger()

        VkInteractor.init(this)
        BranchInteractor.init(this)
    }
}