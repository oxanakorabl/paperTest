package com.paperapp.storage

import android.content.Context
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.paperapp.logger.PaperLoggerFactory

class PushTokenStorage(context: Context) {
    companion object {
        private const val PREFS_KEY = "push_info"
        private const val KEY_TOKEN = "push_token"
    }

    private val logger = PaperLoggerFactory.getLogger("Storage")

    private val rxPrefs =
        RxSharedPreferences.create(context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE))

    fun getToken(): String {
        val token = rxPrefs.getString(KEY_TOKEN, "").get()

        logger.info("Pushes PushTokenStorage getToken: $token")

        return token
    }

    fun saveToken(token: String) {
        logger.info("Pushes PushTokenStorage saveToken: $token")

        rxPrefs.getString(KEY_TOKEN).set(token)
    }

}