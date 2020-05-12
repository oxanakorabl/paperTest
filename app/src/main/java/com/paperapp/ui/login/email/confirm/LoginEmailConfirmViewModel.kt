package com.paperapp.ui.login.email.confirm

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.paperapp.dagger.ComponentHolder
import timber.log.Timber

class LoginEmailConfirmViewModel : ViewModel() {
    private val router = ComponentHolder.loginComponent.getRouter()
    private val loginRepository = ComponentHolder.loginComponent.getLoginRepository()

    var emailAddressField = ObservableField<String>()

    private val emailDisposable =
        loginRepository.savedUserEmail.subscribe(
            { emailAddressField.set(it) },
            { error -> Timber.e(error) })

    fun checkEmail() {
        loginRepository.openMailApp()
    }

    fun sendOneMoreTime() {
        // ToDo: pass proper email
        loginRepository.run {
            requestLoginEmail(
                emailAddress = emailAddressField.get()!!
            )
        }
    }

    fun changeEmail() {
        router.backToEmailForm()
    }

    fun close() {
        router.backToSocial()
    }

    override fun onCleared() {
        emailDisposable.dispose()
        super.onCleared()
    }
}