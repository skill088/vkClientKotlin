package com.projects.vo1.customvk.friends

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.vo1.customvk.R

/**
 * Created by Admin on 21.03.2018.
 */
class FragmentFriendsTabAll : Fragment(), FriendsAllView {

    companion object {

        fun newInstance(): FragmentFriendsTabAll {
            return FragmentFriendsTabAll()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_tab_friends, container, false)
    }
}