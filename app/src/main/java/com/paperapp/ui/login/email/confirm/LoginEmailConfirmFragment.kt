package com.paperapp.ui.login.email.confirm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.paperapp.databinding.LoginEmailConfirmFragmentBinding
import com.paperapp.ui.common.BaseFragment
import com.paperapp.ui.common.ViewModelBuilder

class LoginEmailConfirmFragment : BaseFragment<LoginEmailConfirmFragmentBinding, LoginEmailConfirmViewModel>() {
    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): LoginEmailConfirmFragmentBinding
            = LoginEmailConfirmFragmentBinding.inflate(inflater, container, false)

    override fun buildViewModel(): LoginEmailConfirmViewModel
            = ViewModelBuilder.build(this, LoginEmailConfirmViewModel())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding?.viewmodel = viewModel
    }
}
