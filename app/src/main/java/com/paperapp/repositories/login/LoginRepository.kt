package com.paperapp.repositories.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.paperapp.interactors.*
import com.paperapp.storage.UserInfoStorage
import com.paperapp.ui.login.LoginRouter
import com.paperapp.ui.login.email.form.LoginUser

class LoginRepository(
    private val activity: Activity,
    private val router: LoginRouter,
    private val authInteractor: AuthInteractor,
    private val googleInteractor: GoogleInteractor,
    private val vkInteractor: VkInteractor,
    private val fbInteractor: FacebookInteractor,
    private val branchInteractor: BranchInteractor,
    private val userInfoStorage: UserInfoStorage
) {
    val savedUserEmail = userInfoStorage.email()

    fun saveEmail(email:String) = userInfoStorage.saveEmail(email)

    fun init(loginActivity: Activity, intent: Intent) {
        vkInteractor.init(
            onSuccess = { token ->
                authInteractor.passVkToken(
                    token = token,
                    onSuccess = { router.openMain() }
                )
            },
            onFailure = { errorMessage ->
                router.showSnackBar(errorMessage)
            }
        )

        googleInteractor.init(
            activity = loginActivity,
            onSuccess = { token ->
                authInteractor.passGoogleToken(
                    token = token,
                    onSuccess = { router.openMain() }
                )
            },
            onFailure = { errorMessage ->
                router.showSnackBar(errorMessage)
            }
        )

        fbInteractor.init(
            onSuccess = { token ->
                authInteractor.passFbToken(
                    token = token,
                    onSuccess = { router.openMain() }
                )
            },
            onFailure = { errorMessage ->
                router.showSnackBar(errorMessage)
            }
        )

        intent.data?.let { branchData ->
            // ToDo: loading?

            branchInteractor.init(
                branchData = branchData,
                onSuccess = {
                    router.showLoading()

                    authInteractor.passEmailToken(
                        callbackParameter = it,
                        onSuccess = {
                            router.hideLoading()
                            router.openMain()
                        },
                        onFailure = {
                            router.hideLoading()
                            router.openLoginEmailFail()
                        }
                    )
                },
                onFailure = {
                    router.openLoginEmailFail()
                }
            )
        }
    }

    fun handleResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        var handled = false

        listOf(fbInteractor, vkInteractor, googleInteractor).forEach {
            handled = it.onActivityResult(
                handled = handled,
                requestCode = requestCode,
                resultCode = resultCode,
                intent = data
            )
        }

        return handled
    }

    fun loginGoogle() {
        googleInteractor.login()
    }

    fun loginVK() {
        vkInteractor.login()
    }

    fun loginFB() {
        fbInteractor.login()
    }

    fun loginAsGuest() {
        authInteractor.doGuestAuth(onSuccess = { router.openMain() })
    }

    fun requestLoginEmail(
        emailAddress: String
    ) {
        //TODO make it with NavOptions??? Clear fragments if retry
        router.backToEmailForm()
        router.showLoading()

        saveEmail(emailAddress)

        authInteractor.requestLoginEmail(
            emailAddress = emailAddress,
            onSuccess = {
                router.hideLoading()

                router.openLoginEmailConfirm()
            },
            onFailure = {
                router.hideLoading()

                // ToDo: proper text
                router.showSnackBar("Request login email has failed")
            }
        )
    }

    // ToDo: move mail app call to a separate class
    //TODO move to router
    fun openMailApp() {
        val intent = Intent(Intent.ACTION_MAIN)

        intent.addCategory(Intent.CATEGORY_APP_EMAIL)

        activity.startActivity(
            Intent.createChooser(
                intent,
                ""
            )
        )
    }
}