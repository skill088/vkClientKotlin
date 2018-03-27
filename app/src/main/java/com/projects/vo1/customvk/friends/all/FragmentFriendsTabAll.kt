package com.projects.vo1.customvk.friends.all

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.data.api.friends.ApiFriends
import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.data.nework.ApiInterfaceProvider
import com.projects.vo1.customvk.friends.AdapterFriends
import com.projects.vo1.customvk.friends.FriendInfo
import com.projects.vo1.customvk.friends.FriendInfoCallback
import com.projects.vo1.customvk.friends.FriendsView
import com.projects.vo1.customvk.proffile.FragmentProfile
import kotlinx.android.synthetic.main.fragment_tab_friends.*
import com.projects.vo1.customvk.utils.OnLoadMoreListener


class FragmentFriendsTabAll : Fragment(), FriendsView, FriendInfoCallback {

    private var adapter: AdapterFriends? = null
    private var presenter: PresenterAllFriends? = null
    private var list = mutableListOf<FriendInfo>()

    companion object {

        fun newInstance(): FragmentFriendsTabAll {
            return FragmentFriendsTabAll()
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
        adapter?.setOnClickListener(this)
        friends_recycler_view.adapter = adapter

        presenter = PresenterAllFriends(
            FriendsRepositoryImpl(
                ApiInterfaceProvider.getApiInterface(ApiFriends::class.java)
                , activity!!.applicationContext
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
    }

    override fun showFriends(friends: MutableList<FriendInfo>) {
        list.addAll(friends)
        adapter?.notifyDataSetChanged()
    }

    override fun onClick(id: String?) {
//        activity?.supportFragmentManager
//            ?.beginTransaction()
////            ?.replace(R.id.fragment_container, FragmentProfile.newInstance(id), "ProfileDetails")
//            ?.attach(FragmentProfile.newInstance(id))
//            ?.addToBackStack("")
//            ?.commit()
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

    private fun setAdapterBehaviour() {

        adapter?.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                list.add(FriendInfo())
                adapter?.notifyItemInserted(list.size - 1)
                presenter?.loadMore(adapter?.itemCount!!)
            }
        })
    }

    override fun showMore(friendsList: MutableList<FriendInfo>) {
        list.removeAt(list.size - 1)
        adapter?.notifyItemRemoved(list.size)
        list.addAll(friendsList)
        adapter?.setLoaded()
        adapter?.notifyItemRangeInserted(list.size, 10)
    }
}