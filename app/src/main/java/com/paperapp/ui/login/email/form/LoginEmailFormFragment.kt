package com.paperapp.ui.login.email.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.paperapp.databinding.LoginEmailFormFragmentBinding
import com.paperapp.ui.common.BaseFragment
import com.paperapp.ui.common.ViewModelBuilder

class LoginEmailFormFragment : BaseFragment<LoginEmailFormFragmentBinding, LoginEmailFormViewModel>() {
    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): LoginEmailFormFragmentBinding
            = LoginEmailFormFragmentBinding.inflate(inflater, container, false)

    override fun buildViewModel(): LoginEmailFormViewModel
            = ViewModelBuilder.build(this, LoginEmailFormViewModel())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding?.viewmodel = viewModel
    }
}
