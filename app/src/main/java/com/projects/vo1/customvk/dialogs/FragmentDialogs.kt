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
import com.projects.vo1.customvk.utils.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_dialogs.*
import kotlinx.android.synthetic.main.fragment_error.*

class FragmentDialogs : Fragment(), DialogsView {

    private var adapter: AdapterDialogs? = null
    private var presenter: PresenterDialogs? = null
    private var list = mutableListOf<Message>()

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

        configureScrollListener()

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
                    onLoadMoreListener?.onLoadMore()
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
        private const val visibleThreshold = 3
        private var lastVisibleItem = 0
        private var totalItemCount = 0
        private var onLoadMoreListener: OnLoadMoreListener? = null

        fun newInstance(): FragmentDialogs {
            return FragmentDialogs()
        }
    }
}