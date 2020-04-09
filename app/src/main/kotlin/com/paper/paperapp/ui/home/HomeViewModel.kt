package com.paper.paperapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paper.paperapp.dagger.ComponentHolder

class HomeViewModel : ViewModel() {

    private val context = ComponentHolder.appComponent.getContext()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}