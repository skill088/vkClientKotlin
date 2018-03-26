package com.projects.vo1.customvk.friends

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.data.api.friends.ApiFriends
import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.nework.ApiInterfaceProvider
import kotlinx.android.synthetic.main.fragment_tab_friends.*
import android.widget.Toast
import com.projects.vo1.customvk.activities.MainActivity
import com.projects.vo1.customvk.utils.OnLoadMoreListener



class FragmentFriendsTabAll : Fragment(), FriendsView {

    private var adapter: AdapterFriendsTemp? = null
    private var presenter: PresenterAllFriends? = null
    var tempList = mutableListOf<FriendInfo>()

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

        adapter = AdapterFriendsTemp(friends_recycler_view)
        friends_recycler_view.adapter = adapter

        presenter = PresenterAllFriends(
                FriendsRepositoryImpl(ApiInterfaceProvider.getApiInterface(ApiFriends::class.java)
                        , activity!!.applicationContext), this)
        presenter?.getFriends()
        setAdapterBehaviour()

    }

    override fun onStop() {
        super.onStop()
        presenter?.clearCompositeDesposable()
    }

    override fun hideSwipeRefresh() {
        if  (swipe_refresh.isRefreshing)
            swipe_refresh.isRefreshing = false
    }

    override fun showFriends(friends: List<FriendInfo>) {
        tempList.clear()
        adapter?.setList(friends)
        tempList.addAll(friends)
    }

    private fun setSwipeRefreshBehaviour() {
        swipe_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorAccent))
        swipe_refresh.setOnRefreshListener {
            presenter?.refresh()
        }
    }

    fun setAdapterBehaviour() {

        //set load more listener for the RecyclerView adapter
        adapter?.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                if (tempList.size <= 20) {
                    tempList.add(FriendInfo())
                    adapter?.notifyItemInserted(tempList.size - 1)
                } else {
                    Snackbar.make(friends_recycler_view, "Data is loading", Snackbar.LENGTH_SHORT)
                            .show()
                    Toast.makeText(activity, "Loading data completed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}