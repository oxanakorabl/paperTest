package com.paperapp.ui.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.paperapp.R

class ListsFragment : Fragment() {

    private lateinit var listsViewModel: ListsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listsViewModel =
            ViewModelProviders.of(this).get(ListsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_map, container, false)
        val textView: TextView = root.findViewById(R.id.text)
        listsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}