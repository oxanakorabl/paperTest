package com.paperapp.interactors

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.paperapp.logger.PaperLoggerFactory
import com.paperapp.ui.common.ActivityResultHandlerComponent
import com.paperapp.ui.login.LoginResultHandler

class GoogleInteractor : ActivityResultHandlerComponent {
    companion object {
        private const val GOOGLE_SIGN_IN_REQUEST_CODE = 666
    }

    private val logger = PaperLoggerFactory.getLogger("Auth-Google")

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var loginResultHandler: LoginResultHandler

    private lateinit var onSuccess: (String) -> Unit
    private lateinit var onFailure: (String) -> Unit

    fun init(
        activity: Activity,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        this.onSuccess = onSuccess
        this.onFailure = onFailure

        googleSignInClient = GoogleSignIn.getClient(activity, getGoogleGso())
        loginResultHandler = activity as LoginResultHandler
    }

    fun login() {
        logger.info("Google login was requested")

        val signInIntent: Intent = googleSignInClient.signInIntent
        loginResultHandler.startActivityForLogin(signInIntent, LoginResultHandler.GOOGLE_SIGN_IN)
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
            logger.info("Google login was handled")

            if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(intent)

                try {
                    val account = task.getResult(ApiException::class.java)
                    val token = account?.idToken

                    if (token != null) {
                        onSuccess(token)
                    } else {
                        // ToDo: show proper text
                        // ToDo: error code instead of concrete text
                        onFailure("Google login has finished with an error")
                    }

                } catch (error: ApiException) {
                    logger.error("Google has finished with an error", error)

                    // ToDo: show proper text
                    // ToDo: error code instead of concrete text
                    onFailure("Google has finished with an error")
                }
                true
            } else {
                false
            }
        }
    }

    private fun getGoogleGso(): GoogleSignInOptions {
        // ToDo: move token to resources
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("194862005677-28anu99v1ibo6kei5uri5nrm183kevlc.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }
}