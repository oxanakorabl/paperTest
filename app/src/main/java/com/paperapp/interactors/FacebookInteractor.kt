package com.paperapp.interactors

import android.content.Intent
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.paperapp.dagger.ComponentHolder
import com.paperapp.logger.PaperLoggerFactory
import com.paperapp.ui.common.ActivityResultHandlerComponent
import com.paperapp.ui.login.LoginActivity

class FacebookInteractor() : ActivityResultHandlerComponent {
    private val logger = PaperLoggerFactory.getLogger("Auth-Facebook")
    private var callbackManager: CallbackManager? = null
    private val activity: LoginActivity = ComponentHolder.loginComponent.getActivity()

    fun init(onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        val callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    logger.info("Facebook login has finished successfully")

                    if (loginResult?.accessToken?.token != null) {
                        onSuccess(loginResult.accessToken.token)
                    } else {
                        onFailure("Facebook login has finished with an error")
                    }
                }

                override fun onCancel() {
                    logger.info("Facebook login was canceled")

                    // ToDo: onCancel?
                }

                override fun onError(error: FacebookException) {
                    logger.error("Facebook login has finished with an error", error)

                    //TODO show proper text
                    onFailure("Facebook login has finished with an error")
                }
            })

        this.callbackManager = callbackManager
    }

    fun login() {
        logger.info("Facebook login was requested")

        LoginManager.getInstance().logInWithReadPermissions(
            activity,
            listOf(FacebookPermission.PUBLIC_PROFILE.id)
        )
    }

    override fun onActivityResult(
        handled: Boolean,
        requestCode: Int,
        resultCode: Int,
        intent: Intent?
    ): Boolean {
        return if (handled) {
            true
        } else {
            val result = callbackManager?.onActivityResult(requestCode, resultCode, intent) ?: false

            if (result) {
                logger.info("Facebook result has been handled")
            }

            result
        }
    }
}

private enum class FacebookPermission(val id: String) {
    PUBLIC_PROFILE("public_profile")
}