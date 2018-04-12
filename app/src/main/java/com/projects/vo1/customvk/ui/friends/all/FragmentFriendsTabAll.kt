package com.projects.vo1.customvk.ui.friends.all

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.data.data.api.friends.ApiFriends
import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.data.data.network.ApiInterfaceProvider
import com.projects.vo1.customvk.data.friends.FriendInfo
import com.projects.vo1.customvk.domain.friends.GetAllFriendsUseCase
import com.projects.vo1.customvk.ui.friends.AdapterFriends
import com.projects.vo1.customvk.ui.friends.FriendInfoCallback
import com.projects.vo1.customvk.ui.friends.FriendsView
import com.projects.vo1.customvk.ui.friends.OnLoadMoreListener
import com.projects.vo1.customvk.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.fragment_error.*
import kotlinx.android.synthetic.main.fragment_tab_friends.*

class FragmentFriendsTabAll : Fragment(), FriendsView,
    FriendInfoCallback {

    private var adapter: AdapterFriends? = null
    private var presenter: PresenterAllFriends? = null
    private var list = mutableListOf<FriendInfo>()

    companion object {

        fun newInstance(): FragmentFriendsTabAll {
            return FragmentFriendsTabAll()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_friends, fragment_container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSwipeRefreshBehaviour()
        setRefreshButtonBehaviour()

        val layoutManager = LinearLayoutManager(context)
        friends_recycler_view.layoutManager = layoutManager

        adapter = AdapterFriends(friends_recycler_view, list)
        adapter?.setOnClickListener(this)
        friends_recycler_view.adapter = adapter

        presenter = PresenterAllFriends(
            GetAllFriendsUseCase(
                FriendsRepositoryImpl(
                    ApiInterfaceProvider.getApiInterface(
                        ApiFriends::class.java
                    )
                    , activity!!.applicationContext
                )
            ), this
        )
        presenter?.getFriends()
        setAdapterBehaviour()
    }


    override fun onStop() {
        super.onStop()
        presenter?.clearCompositeDesposable()
    }

    override fun hideSwipeRefresh() {
        if (swipe_refresh.isRefreshing)
            swipe_refresh.isRefreshing = false
        if (error_layout.visibility == View.VISIBLE)
            error_layout.visibility = View.GONE
    }

    override fun showFriends(friends: List<FriendInfo>) {
        list.addAll(friends)
        adapter?.notifyDataSetChanged()
    }

    override fun onClick(id: String?) {
        activity?.startActivity(ProfileActivity.getIntent(id, activity?.applicationContext!!))
    }

    private fun setSwipeRefreshBehaviour() {
        swipe_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorAccent))
        swipe_refresh.setOnRefreshListener {
            list.clear()
            adapter?.notifyDataSetChanged()
            presenter?.refresh()
        }
    }

    private fun setAdapterBehaviour() {

        adapter?.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                list.add(FriendInfo(-1, "", "", ""))
                friends_recycler_view.post({ adapter?.notifyItemInserted(list.size - 1) })
//                adapter?.notifyItemInserted(list.size - 1)
                presenter?.loadMore(adapter?.itemCount!!)
            }
        })
    }

    override fun showMore(friendsList: List<FriendInfo>) {
        list.removeAt(list.size - 1)
        adapter?.notifyItemRemoved(list.size)
        list.addAll(friendsList)
        adapter?.setLoaded()
        adapter?.notifyItemRangeInserted(list.size, 10)
    }

    override fun showError() {
        error_layout.visibility = View.VISIBLE
    }

    private fun setRefreshButtonBehaviour() {
        refresh_button.setOnClickListener({
            Log.d("refresh", "click!")
            presenter?.refresh()
            error_layout.visibility = View.GONE
        })
    }
}