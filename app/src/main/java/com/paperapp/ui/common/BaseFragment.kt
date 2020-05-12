package com.paperapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<X : ViewDataBinding, Y : ViewModel> : Fragment() {
    protected var binding: X? = null

    protected lateinit var viewModel: Y

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): X
    abstract fun buildViewModel(): Y

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflateBinding(inflater, container)

        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = buildViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }
}