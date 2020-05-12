package com.paperapp.interactors

import android.app.Activity
import android.content.Context
import android.net.Uri
import com.paperapp.logger.PaperLoggerFactory
import com.paperapp.ui.login.LoginActivity
import io.branch.referral.Branch
import io.branch.referral.BranchError
import org.json.JSONObject

class BranchInteractor(
    private val loginActivity: LoginActivity
) {
    companion object {
        fun init(context: Context) {
            Branch.enableLogging()
            Branch.getAutoInstance(context)
        }
    }

    private val branchAuthListener =
        StartBranchAuthListener(
            onBranchRegistrationSuccess = { onRegistrationSuccess?.invoke(it) },
            onBranchRegistrationFailure = { onRegistrationFailure?.invoke() }
        )

    private var onRegistrationSuccess: ((String) -> Unit)? = null
    private var onRegistrationFailure: (() -> Unit)? = null

    fun init(
        branchData: Uri,
        onSuccess: (String) -> Unit,
        onFailure: () -> Unit
    ) {
        this.onRegistrationSuccess = onSuccess
        this.onRegistrationFailure = onFailure

        Branch
            .sessionBuilder(loginActivity)
            .withCallback(branchAuthListener)
            .withData(branchData)
            .init()
    }

    fun reInit() {
        Branch
            .sessionBuilder(loginActivity)
            .withCallback(branchAuthListener)
            .reInit()
    }
}

private class StartBranchAuthListener(
    private val onBranchRegistrationSuccess: (String) -> Unit,
    private val onBranchRegistrationFailure: () -> Unit
) : Branch.BranchReferralInitListener {
    private val logger = PaperLoggerFactory.getLogger("OkHttp")

    override fun onInitFinished(referringParams: JSONObject?, error: BranchError?) {
        if (error == null) {
            if (referringParams != null) {
                doOnBranchRegistrationSuccess(referringParams)
            } else {
                logger.error("Branch referring params are not present")

                doOnBranchRegistrationFailure()
            }
        } else {
            logger.error("Branch registration failed. Reason: ${error.errorCode} : ${error.message}")

            doOnBranchRegistrationFailure()
        }
    }

    private fun doOnBranchRegistrationSuccess(referringParams: JSONObject) {
        val canonicalIdentifier = referringParams.get("\$canonical_identifier") as String?
        val feature = referringParams.get("~feature") as String?

        if (feature == "login" || feature == "registration") {
            /**
             * Canonical identifier is provided as query parameters.
             * Fake URL is used only to simplify canonical identifier parsing via existing tools
             */
            val link = "http://fake-url?$canonicalIdentifier"

            val linkUri = Uri.parse(link)

            val param = linkUri.getQueryParameter("p")

            /**
             * Currently registration flag is unused
             * Probably a bug
             */
            @Suppress("UNUSED_VARIABLE")
            val needRegistration = linkUri.getQueryParameter("r") == "1"

            if (param != null) {
                onBranchRegistrationSuccess(param)
            } else {
                logger.error("Branch parameter was not present")

                onBranchRegistrationFailure()
            }
        }
    }

    private fun doOnBranchRegistrationFailure() {
        onBranchRegistrationFailure()
    }

}