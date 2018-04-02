package com.projects.vo1.customvk.dialogs.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.vo1.customvk.R

class FragmentMessages : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    companion object {
        fun newInstance(): FragmentMessages {
            return FragmentMessages()
        }

        fun newInstance(id: Int): FragmentMessages {
            val fragment = FragmentMessages()
            val args = Bundle()
            args.putInt("DIALOG_ID", id)
            fragment.arguments = args
            return fragment
        }
    }
}