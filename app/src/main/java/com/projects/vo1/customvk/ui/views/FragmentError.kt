package com.projects.vo1.customvk.ui.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.vo1.customvk.R

class FragmentError : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    companion object {

        fun newInstance(): FragmentError {
            return FragmentError()
        }
    }
}