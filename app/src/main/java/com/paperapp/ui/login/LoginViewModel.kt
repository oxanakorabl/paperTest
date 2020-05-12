package com.paperapp.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.paperapp.dagger.ComponentHolder

class LoginViewModel : ViewModel() {
    private val authRepository = ComponentHolder.loginComponent.getLoginRepository()

    fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authRepository.handleResult(requestCode, resultCode, data)
    }

    fun initAuth(loginActivity: LoginActivity, intent: Intent) {
        authRepository.init(loginActivity, intent)
    }
}