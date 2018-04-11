package com.projects.vo1.customvk.friends

interface FriendsView {

    fun showFriends(friends: List<FriendInfo>)
    fun hideSwipeRefresh()
    fun showMore(friendsList: List<FriendInfo>)
    fun showError()
}