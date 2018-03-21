package com.projects.vo1.customvk.friends

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.model.Friends
import com.projects.vo1.customvk.nework.ApiInterfaceProvider
import com.projects.vo1.customvk.data.api.friends.ApiFriends
import com.projects.vo1.customvk.data.friends.FriendsRepository

import kotlinx.android.synthetic.main.fragment_friends.*


class FragmentFriends : Fragment() {

    companion object {

        fun newInstance(): FragmentFriends {
            return FragmentFriends()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater?.inflate(R.layout.fragment_friends, container, false)
        val activity = activity as AppCompatActivity
        activity.supportActionBar?.title  = resources.getString(R.string.menu_frineds)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureTabLayout()
    }



    private fun configureTabLayout() {
        pager.adapter = PagerAdapterFriends(fragmentManager)
        tab_layout.setupWithViewPager(pager)
    }


}