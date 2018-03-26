package com.projects.vo1.customvk.friends

import android.view.View

/**
 * Created by Admin on 21.03.2018.
 */
interface FriendsView {

    fun showFriends(friends: MutableList<FriendInfo>)
    fun hideSwipeRefresh()
    fun showMore(friendsList: MutableList<FriendInfo>)
}