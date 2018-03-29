package com.projects.vo1.customvk.dialogs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.data.api.dialogs.ApiDialogs
import com.projects.vo1.customvk.data.dialogs.DialogsRepositoryImpl
import com.projects.vo1.customvk.data.network.ApiInterfaceProvider
import com.projects.vo1.customvk.utils.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_dialogs.*
import kotlinx.android.synthetic.main.fragment_error.*

class FragmentDialogs : Fragment(), DialogsView {

    private var adapter: AdapterDialogs? = null
    private var presenter: PresenterDialogs? = null
    private var list = mutableListOf<Message>()


    private var onLoadMoreListener = object : OnLoadMoreListener {
        override fun onLoadMore() {
            list.add(Message())
            messages_recycler_view.post({ adapter?.notifyItemInserted(list.size - 1) })
            presenter?.loadMore(adapter?.itemCount!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialogs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRefreshButtonBehaviour()

        val layoutManager = LinearLayoutManager(context)
        messages_recycler_view.layoutManager = layoutManager

        adapter = AdapterDialogs(list)
        messages_recycler_view.adapter = adapter

        presenter = PresenterDialogs(
            DialogsRepositoryImpl(
                ApiInterfaceProvider.getApiInterface(
                    ApiDialogs::class.java),
                    activity?.applicationContext!!),
                this)

        configureScrollListener()
        setRefreshButtonBehaviour()

        presenter?.getDialogs()
    }

    override fun showMessages(messages: MutableList<Message>) {
        val size = list.size
        list.addAll(messages)
        adapter?.notifyItemRangeInserted(size, 20)
    }

    override fun showError() {
        error_layout.visibility = View.VISIBLE
    }

    override fun showMore(messages: MutableList<Message>) {
        list.removeAt(list.size - 1)
        adapter?.notifyItemRemoved(list.size)
        list.addAll(messages)
        isLoading = false
        adapter?.notifyItemRangeInserted(list.size, 10)
    }

    private fun configureScrollListener() {
        val linearLayoutManager = messages_recycler_view.layoutManager as LinearLayoutManager
        messages_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    if (lastVisibleItem == -1)
                        return
                    onLoadMoreListener.onLoadMore()
                    isLoading = true
                }
            }
        })
    }

    private fun setRefreshButtonBehaviour() {
        refresh_button.setOnClickListener({
            Log.d("refresh", "click!")
//            presenter?.refresh()
            error_layout.visibility = View.GONE
        })
    }

    companion object {

        private var isLoading = false
        private const val visibleThreshold = 5
        private var lastVisibleItem = 0
        private var totalItemCount = 0

        fun newInstance(): FragmentDialogs {
            return FragmentDialogs()
        }
    }
}