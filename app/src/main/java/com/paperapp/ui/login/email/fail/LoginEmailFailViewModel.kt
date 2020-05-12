package com.paperapp.ui.login.email.fail

import androidx.lifecycle.ViewModel
import com.paperapp.dagger.ComponentHolder

class LoginEmailFailViewModel : ViewModel() {
    private val router = ComponentHolder.loginComponent.getRouter()
    private val loginRepository = ComponentHolder.loginComponent.getLoginRepository()

    fun retry() {
        loginRepository.openMailApp()
    }

    fun resend() {
        router.backToEmailForm()
    }

    fun close() {
        router.backToSocial()
    }
}