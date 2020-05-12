package com.paperapp.interactors

import android.content.Context
import android.content.Intent
import com.paperapp.dagger.ComponentHolder
import com.paperapp.logger.PaperLoggerFactory
import com.paperapp.ui.common.ActivityResultHandlerComponent
import com.paperapp.ui.login.LoginActivity
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError

class VkInteractor : ActivityResultHandlerComponent {
    companion object {
        fun init(context: Context) {
            VKSdk.initialize(context)
        }
    }

    private val logger = PaperLoggerFactory.getLogger("Auth-VK")
    private val activity: LoginActivity = ComponentHolder.loginComponent.getActivity()

    private lateinit var onSuccess: (VKAccessToken) -> Unit
    private lateinit var onFailure: (String) -> Unit

    fun init(
        onSuccess: (VKAccessToken) -> Unit,
        onFailure: (String) -> Unit
    ) {
        this.onSuccess = onSuccess
        this.onFailure = onFailure
    }

    fun login() {
        logger.info("VK login was requested")

        VKSdk.login(activity, VKScope.FRIENDS, VKScope.EMAIL)
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
            VKSdk.onActivityResult(
                requestCode,
                resultCode,
                intent,
                object : VKCallback<VKAccessToken?> {
                    override fun onResult(res: VKAccessToken?) {
                        logger.info("VK login has finished successfully")

                        if (res != null) {
                            onSuccess(res)
                        } else {
                            //TODO show proper text
                            onFailure("VK login has finished with an error")
                        }
                    }

                    override fun onError(error: VKError) {
                        logger.error("VK login has finished with an error. ${error.errorMessage}")

                        //TODO show proper text
                        onFailure("VK login has finished with an error")
                    }
                })
        }
    }
}