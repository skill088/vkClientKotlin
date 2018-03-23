package com.projects.vo1.customvk.friends

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.data.api.friends.ApiFriends
import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.nework.ApiInterfaceProvider
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_tab_friends.*


class FragmentFriendsTabOnline : Fragment(), FriendsView {

    private val adapter = AdapterFriends()
    private var presenter: PresenterFriendsOnline? = null

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
        friends_recycler_view.adapter = adapter

        presenter = PresenterFriendsOnline(
                FriendsRepositoryImpl(ApiInterfaceProvider.getApiInterface(ApiFriends::class.java)
                        , activity!!.applicationContext), this)
        presenter?.getOnlineFriends()
    }

    override fun showFriends(friends: List<FriendInfo>) {
        adapter.setList(friends)
    }

    override fun onStop() {
        super.onStop()
        presenter?.clearCompositeDesposable()
    }

    private fun setSwipeRefreshBehaviour() {
        swipe_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorAccent))
        swipe_refresh.setOnRefreshListener {
            presenter?.refresh()
        }
    }

    override fun hideSwipeRefresh() {
        if  (swipe_refresh.isRefreshing)
            swipe_refresh.isRefreshing = false
    }
}