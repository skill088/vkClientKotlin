package com.projects.vo1.customvk.ui.friends

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.projects.vo1.customvk.ui.friends.all.FragmentFriendsTabAll
import com.projects.vo1.customvk.ui.friends.online.FragmentFriendsTabOnline

/**
 * Created by Admin on 21.03.2018.
 */
class PagerAdapterFriends(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val countOfTab: Int = 2
    private val tabTitles =  arrayOf("Все", "Онлайн")

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> FragmentFriendsTabAll.newInstance()
            1 -> FragmentFriendsTabOnline.newInstance()
            else -> null
        }
    }

    override fun getCount(): Int {
        return countOfTab
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitles[position]
    }
}