package com.paperapp.storage

import android.content.Context
import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.Observable

class UserInfoStorage(context: Context) {

    companion object {
        private const val PREFS_KEY = "user_info"
        private const val KEY_TOKEN = "token"
        private const val KEY_CLIENT_ID = "client_id"
        private const val KEY_PHONE_NUMBER = "phone_number"
        private const val KEY_EMAIL = "email"
    }

    private val rxPrefs =
        RxSharedPreferences.create(context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE))

    fun getToken(): String {
        return rxPrefs.getString(KEY_TOKEN, "").get()
    }

    fun getClientId(): String {
        return rxPrefs.getString(KEY_CLIENT_ID, "").get()
    }

    fun getPhoneNumber(): String {
        return rxPrefs.getString(KEY_PHONE_NUMBER, "").get()
    }

    fun getEmail(): String {
        return rxPrefs.getString(KEY_EMAIL, "").get()
    }

    fun saveEmail(email: String) {
        rxPrefs.getString(KEY_EMAIL).set(email)
    }

    fun saveToken(token: String) {
        rxPrefs.getString(KEY_TOKEN).set(token)
    }

    fun saveClientId(clientId: String) {
        rxPrefs.getString(KEY_CLIENT_ID).set(clientId)
    }

    fun savePhoneNumber(phoneNumber: String) {
        rxPrefs.getString(KEY_PHONE_NUMBER).set(phoneNumber)
    }


    fun clear() {
        saveToken("")
        saveClientId("")
        savePhoneNumber("")
    }

    fun token(): Observable<String> {
        return rxPrefs.getString(KEY_TOKEN, "").asObservable()
    }

    fun email(): Observable<String> {
        return rxPrefs.getString(KEY_EMAIL, "").asObservable()
    }

}