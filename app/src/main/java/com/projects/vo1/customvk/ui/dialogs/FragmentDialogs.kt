package com.projects.vo1.customvk.ui.dialogs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.data.data.api.dialogs.ApiDialogs
import com.projects.vo1.customvk.data.data.api.friends.ApiFriends
import com.projects.vo1.customvk.data.data.dialogs.DialogsRepositoryImpl
import com.projects.vo1.customvk.data.data.network.ApiInterfaceProvider
import com.projects.vo1.customvk.data.device.services.DialogsService.Companion.MESSAGE_NOTIFICATION
import com.projects.vo1.customvk.data.device.services.DialogsService.Companion.intentFilter
import com.projects.vo1.customvk.data.dialogs.Dialog
import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.data.longPolling.MessageNotification
import com.projects.vo1.customvk.domain.dialogs.GetDialogsUseCase
import com.projects.vo1.customvk.domain.dialogs.GetInterlocutorsProfiles
import com.projects.vo1.customvk.ui.dialogs.details.MessagesActivity
import com.projects.vo1.customvk.ui.friends.OnLoadMoreListener
import com.projects.vo1.customvk.utils.GlideApp
import kotlinx.android.synthetic.main.fragment_dialogs.*
import kotlinx.android.synthetic.main.fragment_error.*

class FragmentDialogs : Fragment(), DialogsView,
    OnDialogClickListener {

    private var adapter: AdapterDialogs? = null
    private var presenter: PresenterDialogs? = null
    private var list = mutableListOf<Dialog>()

    lateinit var br: BroadcastReceiver


    private var onLoadMoreListener = object :
        OnLoadMoreListener {
        override fun onLoadMore() {
            list.add(
                Dialog(
                    -1, -1, -1, -1, -1, "", "",
                    null, null, null, null
                )
            )
            dialogs_recycler_view.post({ adapter?.notifyItemInserted(list.size - 1) })
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
        dialogs_recycler_view.layoutManager = layoutManager

        adapter = AdapterDialogs(list, GlideApp.with(this))
        dialogs_recycler_view.adapter = adapter

        presenter = PresenterDialogs(
            GetDialogsUseCase(
                DialogsRepositoryImpl(
                    ApiInterfaceProvider.getApiInterface(
                        ApiDialogs::class.java
                    ),
                    activity?.applicationContext!!
                )
            ),
            GetInterlocutorsProfiles(
                FriendsRepositoryImpl(
                    ApiInterfaceProvider.getApiInterface(
                        ApiFriends::class.java
                    ),
                    activity?.applicationContext!!
                )
            ),
            this
        )

        configureScrollListener()
        setRefreshButtonBehaviour()
        adapter?.setClickListener(this)

        presenter?.getDialogs()


        // создаем BroadcastReceiver
        br = object : BroadcastReceiver() {
            // действия при получении сообщений
            override fun onReceive(context: Context, intent: Intent) {
                val message = intent.getParcelableExtra<MessageNotification>(MESSAGE_NOTIFICATION)
                val newDialog =
                    list.find {
                        (it.userId == message.interlocutorId && it.chatId == null)
                                || it.chatId == message.interlocutorId
                    }
                if (newDialog != null) {
                    val pos = list.indexOf(newDialog)
                    if (list.remove(newDialog)) {
                        newDialog.body = message.msgBody
                        newDialog.date = message.msgTime
                        list.add(0, newDialog)
                        when(pos) {
                            0 -> adapter?.notifyItemChanged(0)
                            else -> {
                                adapter?.notifyItemMoved(pos, 0)
                                adapter?.notifyItemChanged(0)
                            }

                        }
                        dialogs_recycler_view.scrollToPosition(0)
                    }
                } else {
                    presenter?.reload()
                }
            }
        }
    }

    override fun onResume() {
        activity?.registerReceiver(br, intentFilter)
        super.onResume()
    }

    override fun onDestroy() {
        activity?.unregisterReceiver(br)
        super.onDestroy()
    }

    override fun showMessages(dialogs: List<Dialog>) {
        val size = list.size
        list.addAll(dialogs)
        adapter?.notifyItemRangeInserted(size, 20)
    }

    override fun reload(dialogs: List<Dialog>) {
        list.clear()
        list.addAll(dialogs)
        adapter?.notifyDataSetChanged()
    }

    override fun showError() {
        error_layout.visibility = View.VISIBLE
    }

    override fun showMore(dialogs: List<Dialog>) {
        list.removeAt(list.size - 1)
        adapter?.notifyItemRemoved(list.size)
        list.addAll(dialogs)
        isLoading = false
        adapter?.notifyItemRangeInserted(list.size, 10)
    }

    private fun configureScrollListener() {
        val linearLayoutManager = dialogs_recycler_view.layoutManager as LinearLayoutManager
        dialogs_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    override fun onClick(id: Long, name: String, isChat: Boolean) {
        activity?.startActivity(MessagesActivity.getIntent(id, name, isChat, context!!))
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