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
import com.projects.vo1.customvk.data.nework.ApiInterfaceProvider
import android.support.v7.widget.LinearLayoutManager
import com.projects.vo1.customvk.friends.AdapterFriends
import com.projects.vo1.customvk.friends.FriendInfo
import com.projects.vo1.customvk.friends.FriendsView
import com.projects.vo1.customvk.utils.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_tab_friends.*


class FragmentFriendsTabOnline : Fragment(), FriendsView {

    private var adapter: AdapterFriends? = null
    private var presenter: PresenterFriendsOnline? = null
    private var list = mutableListOf<FriendInfo>()

    companion object {

        fun newInstance(): FragmentFriendsTabOnline {
            return FragmentFriendsTabOnline()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSwipeRefreshBehaviour()

        val layoutManager = LinearLayoutManager(context)
        friends_recycler_view.layoutManager = layoutManager

        adapter = AdapterFriends(friends_recycler_view, list)
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

    override fun showFriends(friends: MutableList<FriendInfo>) {
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
//            adapter?.notifyItemRangeRemoved(0, adapter?.itemCount!!)
            adapter?.notifyDataSetChanged()
            presenter?.refresh()
        }
    }

    override fun hideSwipeRefresh() {
        if  (swipe_refresh.isRefreshing)
            swipe_refresh.isRefreshing = false
    }

    override fun showMore(friendsList: MutableList<FriendInfo>) {
        list.removeAt(list.size - 1)
        adapter?.notifyItemRemoved(list.size)
        list.addAll(friendsList)
        adapter?.setLoaded()
        adapter?.notifyItemRangeInserted(list.size,  10)
    }

    private fun setAdapterBehaviour() {

        adapter?.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                list.add(FriendInfo())
                adapter?.notifyItemInserted(list.size - 1)
                presenter?.loadMore(adapter?.itemCount!!)
            }
        })
    }
}