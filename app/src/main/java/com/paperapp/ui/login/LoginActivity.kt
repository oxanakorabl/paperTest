package com.paperapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.paperapp.R
import com.paperapp.dagger.ComponentHolder
import com.paperapp.ui.common.ViewModelBuilder

class LoginActivity : AppCompatActivity(), LoginResultHandler {
    companion object {
        lateinit var INSTANCE: LoginActivity
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        INSTANCE = this

        ComponentHolder.createLoginComponent()
        setContentView(R.layout.activity_login)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        viewModel = ViewModelBuilder.build(this, LoginViewModel())

        viewModel.initAuth(this, intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        viewModel.handleResult(requestCode, resultCode, data)
    }

    override fun startActivityForLogin(intent: Intent?, requestCode: Int) {
        startActivityForResult(intent, requestCode)
    }

    override fun onDestroy() {
        ComponentHolder.releaseLoginComponent()

        super.onDestroy()
    }
}

interface LoginResultHandler {
    companion object {
        const val GOOGLE_SIGN_IN = 666
    }

    fun startActivityForLogin(intent: Intent?, requestCode: Int)
}