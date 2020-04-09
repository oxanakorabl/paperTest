package com.paper.paperapp.storage

import android.content.Context
import com.f2prateek.rx.preferences2.RxSharedPreferences
import timber.log.Timber

class PushTokenStorage(context: Context) {

    companion object {
        private const val PREFS_KEY = "push_info"
        private const val KEY_TOKEN = "push_token"
    }

    private val rxPrefs = RxSharedPreferences.create(context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE))

    fun getToken(): String {
        val token = rxPrefs.getString(KEY_TOKEN, "").get()
        Timber.d("Pushes PushTokenStorage getToken: $token")
        return token
    }

    fun saveToken(token: String) {
        Timber.d("Pushes PushTokenStorage saveToken: $token")
        rxPrefs.getString(KEY_TOKEN).set(token)
    }

}