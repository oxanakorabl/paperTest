package com.paperapp.ui.login.social

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.paperapp.databinding.LoginSocialFragmentBinding
import com.paperapp.ui.common.BaseFragment
import com.paperapp.ui.common.ViewModelBuilder

class LoginSocialFragment : BaseFragment<LoginSocialFragmentBinding, LoginSocialViewModel>() {
    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): LoginSocialFragmentBinding
            = LoginSocialFragmentBinding.inflate(inflater, container, false)

    override fun buildViewModel(): LoginSocialViewModel
            = ViewModelBuilder.build(this, LoginSocialViewModel())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding?.viewmodel = viewModel
    }
}