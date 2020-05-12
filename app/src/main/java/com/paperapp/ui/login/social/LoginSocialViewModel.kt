package com.paperapp.ui.login.social

import androidx.lifecycle.ViewModel
import com.paperapp.dagger.ComponentHolder

class LoginSocialViewModel : ViewModel() {
    // ToDo: do proper DI
    private val router = ComponentHolder.loginComponent.getRouter()
    private val loginRepository = ComponentHolder.loginComponent.getLoginRepository()

    fun loginVK() {
        loginRepository.loginVK()
    }

    fun loginFB() {
        loginRepository.loginFB()
    }

    fun loginGoogle() {
        // TODO show progress
        loginRepository.loginGoogle()
    }

    fun openEmailLogin() {
        router.openLoginEmailForm()
    }

    fun loginAsGuest() {
        loginRepository.loginAsGuest()
    }
}