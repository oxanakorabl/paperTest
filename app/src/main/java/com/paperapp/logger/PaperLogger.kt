package com.paperapp.logger

import timber.log.Timber

class PaperLogger(private val tag: String) {
    fun info(message: String) {
        Timber.tag(tag).i(message)
    }

    fun error(message: String) {
        Timber.tag(tag).e(message)
    }

    fun error(message: String, throwable: Throwable) {
        Timber.tag(tag).e(throwable, message)
    }

    fun warning(message: String) {
        Timber.tag(tag).w(message)
    }
}