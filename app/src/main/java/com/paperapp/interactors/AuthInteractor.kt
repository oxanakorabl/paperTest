package com.paperapp.interactors

import com.paperapp.configuration.ConfigurationOfBackend
import com.paperapp.logger.PaperLoggerFactory
import com.paperapp.network.api.AuthApi
import com.paperapp.network.dto.login.LoginViaEmailBody
import com.vk.sdk.VKAccessToken
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthInteractor(private val authApi: AuthApi) {
    private val logger = PaperLoggerFactory.getLogger("Auth-Interactor")

    /* ToDo: notify about failures? */
    fun doGuestAuth(onSuccess: () -> Unit) {
        logger.info("doGuestAuth")

        val configuration = ConfigurationOfBackend.getCurrent()
        /* ToDo: handle disposable */
        val disposable = authApi.loginAsGuest(
            clientId = configuration.clientId,
            clientSecret = configuration.apiKey
        )
            .subscribeOn(Schedulers.io())
            .subscribe(
                { baseResponse ->
                    baseResponse
                        .handle(name = "Guest auth")
                        .onSuccess {
                            onSuccess()
                        }
                        .onAnyFailure { /* ToDo */ }
                },
                { error -> logger.error("Guest auth request has failed", error) }
            )
    }

    fun requestLoginEmail(
        emailAddress: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        logger.info("Login email was requested")

        // ToDo: handle disposable
        val disposable = authApi.requestLoginEmail(
            LoginViaEmailBody(
                emailAddress = emailAddress
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
            { baseResponse ->
                baseResponse.handle("Request login email")
                    .onSuccess { onSuccess() }
                    .onAnyFailure { onFailure() }
            },
            {
                onFailure()
            }
        )
    }

    fun passEmailToken(callbackParameter: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val configuration = ConfigurationOfBackend.getCurrent()

        //TODO handle disposable
        val disposable = authApi.loginViaEmailConfirmation(
            clientId = configuration.clientId,
            clientSecret = configuration.apiKey,
            callbackParameter = callbackParameter
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { baseResponse ->
                    baseResponse
                        .handle("Pass email token")
                        .onSuccess { onSuccess() }
                        .onAnyFailure { onFailure() }
                },
                {
                    onFailure()
                }
            )
    }

    fun passGoogleToken(token: String, onSuccess: () -> Unit) {
        val configuration = ConfigurationOfBackend.getCurrent()

        //TODO handle disposable
        val disposable = authApi.loginGoogle(
            clientId = configuration.clientId,
            clientSecret = configuration.apiKey,
            token = token
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _ ->
                //TODO save staff in userINfoStorage
                onSuccess()
            }
    }

    fun passVkToken(token: VKAccessToken, onSuccess: () -> Unit) {
        val configuration = ConfigurationOfBackend.getCurrent()

        //TODO handle disposable
        val disposable = authApi.loginVK(
            clientId = configuration.clientId,
            clientSecret = configuration.apiKey,
            token = token.accessToken,
            email = token.email,
            userId = token.userId,
            link_confirmed = true
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                //TODO baseresponce is not working in proper way, 400 is not handled
                response.success?.data?.let {
                    //TODO save staff in userINfoStorage
                    onSuccess()
                }
            }
    }

    fun passFbToken(token: String, onSuccess: () -> Unit) {
        //TODO clear disposable
        val configuration = ConfigurationOfBackend.getCurrent()

        val disposable = authApi.loginFB(
            clientId = configuration.clientId,
            clientSecret = configuration.apiKey,
            token = token,
            link_confirmed = true
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                response.success?.data?.let {
                    //TODO save staff in userINfoStorage
                    onSuccess()
                }
            }
    }
}