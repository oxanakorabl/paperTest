package com.paperapp.dagger

import com.paperapp.dagger.modules.LoginModule

object ComponentHolder {
    val appComponent: AppComponent = DaggerAppComponent.create()

    private var _loginComponent: LoginComponent? = null
    val loginComponent: LoginComponent
        get() {
            if (_loginComponent == null) {
                createLoginComponent()
            }
            return _loginComponent!!
        }

    fun createLoginComponent() {
        _loginComponent = appComponent.createLoginComponent(LoginModule())
    }

    fun releaseLoginComponent() {
        _loginComponent = null
    }

}
