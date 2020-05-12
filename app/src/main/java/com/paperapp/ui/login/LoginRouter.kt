package com.paperapp.ui.login

import android.app.Activity
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.paperapp.R
import com.paperapp.dagger.ComponentHolder
import com.paperapp.ui.MainActivity


class LoginRouter {
    private val activity = ComponentHolder.loginComponent.getActivity()
    private val navController = activity.findNavController(R.id.nav_host_fragment)
    private val root = activity.findViewById<CoordinatorLayout>(R.id.login_activity_root)
    private val loading = activity.findViewById<ProgressBar>(R.id.progressBar)

    fun openLoginSocial() = navController.navigate(R.id.navigation_login_social)
    fun openLoginEmailForm() = navController.navigate(R.id.navigation_login_email_form)
    fun openLoginEmailConfirm() = navController.navigate(R.id.navigation_login_email_confirm)
    fun openLoginEmailFail() = navController.navigate(R.id.navigation_login_email_fail)

    fun backToSocial() {
        val navigate = navController.popBackStack(R.id.navigation_login_social, false)
        if (!navigate) {
            openLoginSocial()
        }
    }

    fun backToEmailForm() {
        if (navController.currentDestination?.id != R.id.navigation_login_email_form) {
            val navigate = navController.popBackStack(R.id.navigation_login_email_form, false)
            if (!navigate) {
                openLoginEmailForm()
            }
        }
    }

    fun openMain() {
        activity.startActivity(Intent(activity, MainActivity::class.java))
    }

    fun showSnackBar(text: String) {
        val snackbar =  Snackbar.make(root, text, Snackbar.LENGTH_SHORT)

        val layoutParams = snackbar.view.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.anchorId = R.id.snack_anchor //Id for your bottomNavBar or TabLayout
        layoutParams.anchorGravity = Gravity.TOP
        layoutParams.gravity = Gravity.TOP
        snackbar.view.layoutParams = layoutParams

        snackbar.show()
    }

    fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        loading.visibility = View.GONE
    }

    fun hideKeyboard() {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        val view = activity.currentFocus ?: View(activity)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}


