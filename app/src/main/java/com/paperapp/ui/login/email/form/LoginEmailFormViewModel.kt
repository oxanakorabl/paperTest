package com.paperapp.ui.login.email.form

import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paperapp.dagger.ComponentHolder
import com.paperapp.repositories.login.LoginRepository

class LoginEmailFormViewModel : ViewModel() {
    private val router = ComponentHolder.loginComponent.getRouter()
    private val loginRepository: LoginRepository =
        ComponentHolder.loginComponent.getLoginRepository()

    var emailAddressField = ObservableField<String>()

    var error = ObservableField<String>()

    private val userMutableLiveData: MutableLiveData<LoginUser>
        get() = MutableLiveData(
            LoginUser(
                emailAddress = (emailAddressField.get() ?: "").trim()
            )
        )

    fun close() {
        router.hideKeyboard()
        router.backToSocial()
    }

    fun loginViaEmail() {
        val proceed =
            userMutableLiveData.value != null && userMutableLiveData.value!!.isEmailValid()

        if (proceed) {
            error.set(null)
            router.hideKeyboard()
            val emailAddress = userMutableLiveData.value?.emailAddress

            if (emailAddress != null) {
                loginRepository.requestLoginEmail(
                    emailAddress = emailAddress
                )
            }
        } else {
            //TODO add correct text
            error.set("Введите валидный email")
        }
    }
}

class LoginUser(val emailAddress: String?) {
    fun isEmailValid(): Boolean {
        return if (emailAddress.isNullOrBlank()) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
        }
    }
}