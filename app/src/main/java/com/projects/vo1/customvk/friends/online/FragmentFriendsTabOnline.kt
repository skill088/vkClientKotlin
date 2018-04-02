package com.projects.vo1.customvk.friends.online

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.data.api.friends.ApiFriends
import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.data.network.ApiInterfaceProvider
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.projects.vo1.customvk.friends.AdapterFriends
import com.projects.vo1.customvk.friends.FriendInfo
import com.projects.vo1.customvk.friends.FriendInfoCallback
import com.projects.vo1.customvk.friends.FriendsView
import com.projects.vo1.customvk.profile.ProfileActivity
import com.projects.vo1.customvk.utils.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_error.*
import kotlinx.android.synthetic.main.fragment_error.view.*
import kotlinx.android.synthetic.main.fragment_tab_friends.*


class FragmentFriendsTabOnline : Fragment(), FriendsView, FriendInfoCallback {

    private var adapter: AdapterFriends? = null
    private var presenter: PresenterFriendsOnline? = null
    private var list = mutableListOf<FriendInfo>()

    companion object {

        fun newInstance(): FragmentFriendsTabOnline {
            return FragmentFriendsTabOnline()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSwipeRefreshBehaviour()
        setRefreshButtonBehaviour()

        val layoutManager = LinearLayoutManager(context)
        friends_recycler_view.layoutManager = layoutManager

        adapter = AdapterFriends(friends_recycler_view, list)
        adapter?.setOnClickListener(this)
        setAdapterBehaviour()
        friends_recycler_view.adapter = adapter

        presenter = PresenterFriendsOnline(
            FriendsRepositoryImpl(
                ApiInterfaceProvider.getApiInterface(ApiFriends::class.java)
                , activity!!.applicationContext
            ), this
        )
        presenter?.getOnlineFriends()
    }

    override fun showFriends(friends: List<FriendInfo>) {
        list.addAll(friends)
        adapter?.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        presenter?.clearCompositeDesposable()
    }

    private fun setSwipeRefreshBehaviour() {
        swipe_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorAccent))
        swipe_refresh.setOnRefreshListener {
            list.clear()
            adapter?.notifyDataSetChanged()
            presenter?.refresh()
        }
    }

    override fun hideSwipeRefresh() {
        if (swipe_refresh.isRefreshing)
            swipe_refresh.isRefreshing = false
        if (error_layout.visibility == View.VISIBLE)
            error_layout.visibility = View.GONE
    }

    override fun showMore(friendsList: List<FriendInfo>) {
        list.removeAt(list.size - 1)
        adapter?.notifyItemRemoved(list.size)
        list.addAll(friendsList)
        adapter?.setLoaded()
        adapter?.notifyItemRangeInserted(list.size, 10)
    }

    override fun onClick(id: String?) {
        activity?.startActivity(ProfileActivity.getIntent(id, activity?.applicationContext!!))
    }

    override fun showError() {
        error_layout.visibility = View.VISIBLE
    }

    private fun setAdapterBehaviour() {

        adapter?.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                list.add(FriendInfo(-1,"", "", ""))
                friends_recycler_view.post({ adapter?.notifyItemInserted(list.size - 1) })
                presenter?.loadMore(adapter?.itemCount!!)
            }
        })
    }

    private fun setRefreshButtonBehaviour() {
        error_layout.refresh_button.setOnClickListener({
            Log.d("refresh", "click!")
            presenter?.refresh()
            error_layout.visibility = View.GONE
        })
    }
}