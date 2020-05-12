package com.paperapp.network.interceptors

import com.paperapp.logger.PaperLoggerFactory
import okhttp3.logging.HttpLoggingInterceptor

object SliceLinesInterceptor {
    private val logger = PaperLoggerFactory.getLogger("OkHttp")


    fun getInterceptor(): HttpLoggingInterceptor {
        val log = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                sliceLongResponseIntoLines(message)
            }
        })

        log.level = HttpLoggingInterceptor.Level.BODY
        return log
    }

    private fun sliceLongResponseIntoLines(str: String) {
        when {
            str.contains("�") -> {
                // do nothing
            }
            str.length > 3000 -> {
                logger.info(str.substring(0, 3000))

                sliceLongResponseIntoLines(str.substring(3000))
            }
            else -> {
                logger.info(str)
            }
        }
    }
}
