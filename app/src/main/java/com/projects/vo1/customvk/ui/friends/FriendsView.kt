package com.projects.vo1.customvk.ui.friends

import com.projects.vo1.customvk.data.friends.FriendInfo

interface FriendsView {

    fun showFriends(friends: List<FriendInfo>)
    fun hideSwipeRefresh()
    fun showMore(friendsList: List<FriendInfo>)
    fun showError()
}