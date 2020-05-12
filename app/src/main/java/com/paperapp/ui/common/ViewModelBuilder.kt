package com.paperapp.ui.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

object ViewModelBuilder {
    fun <T : ViewModel> build(
        owner: ViewModelStoreOwner,
        instance: T
    ): T {
        val factory = object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return if (modelClass == instance.javaClass) {
                    instance as T
                } else {
                    throw IllegalArgumentException("ViewModel Not Found")
                }
            }
        }

        return ViewModelProvider(owner, factory).get(instance.javaClass)
    }
}