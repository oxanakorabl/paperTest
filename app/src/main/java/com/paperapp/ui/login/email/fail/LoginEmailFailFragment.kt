package com.paperapp.ui.login.email.fail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.paperapp.databinding.LoginEmailFailFragmentBinding
import com.paperapp.ui.common.BaseFragment
import com.paperapp.ui.common.ViewModelBuilder

class LoginEmailFailFragment : BaseFragment<LoginEmailFailFragmentBinding, LoginEmailFailViewModel>() {
    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): LoginEmailFailFragmentBinding
            = LoginEmailFailFragmentBinding.inflate(inflater, container, false)

    override fun buildViewModel(): LoginEmailFailViewModel
            = ViewModelBuilder.build(this, LoginEmailFailViewModel())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding?.viewmodel = viewModel
    }
}