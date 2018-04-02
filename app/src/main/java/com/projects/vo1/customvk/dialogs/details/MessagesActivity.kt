package com.projects.vo1.customvk.dialogs.details

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.ShareActionProvider
import android.view.Menu
import android.view.MenuItem
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.data.api.dialogs.ApiDialogs
import com.projects.vo1.customvk.data.api.friends.ApiFriends
import com.projects.vo1.customvk.data.dialogs.details.DialogDetailRepositoryImpl
import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.data.network.ApiInterfaceProvider
import com.projects.vo1.customvk.utils.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_messages.*
import java.util.*

class MessagesActivity : AppCompatActivity(), MessagesView {

    private var presenter: PresenterMessages? = null
    private var adapter: AdapterMessages? = null
    private val list = mutableListOf<Message>()
    private var miShareAction: ShareActionProvider? = null


    private var isLoading = false
    private val visibleThreshold = 5
    private var lastVisibleItem = 0
    private var totalItemCount = 0


    private var onLoadMoreListener = object : OnLoadMoreListener {
        override fun onLoadMore() {
            list.add(
                Message(
                    -1, "", -1, -1, -1, -1, -1,
                    -1, null
                )
            )
            messages_recycler_view.post({ adapter?.notifyItemInserted(list.size - 1) })
            presenter?.loadMore(adapter?.itemCount!!, intent.extras.getLong(INTENT_KEY_UID))
        }
    }

    override fun setSended() {
        list.removeAll(list)
        presenter?.getHistory(intent.extras.getLong(INTENT_KEY_UID))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        supportActionBar?.title = intent.extras.getString(INTENT_KEY_UNAME).toString()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        adapter = AdapterMessages(list)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        messages_recycler_view.layoutManager = layoutManager
        messages_recycler_view.adapter = adapter
        configureScrollListener()

        send.setOnClickListener {
            if (input_message.text.toString().isEmpty())
                return@setOnClickListener

            when(intent.extras.getBoolean(INTENT_KEY_CHAT)) {
                true -> {
                    presenter?.sendMessage(
                        null,
                        intent.extras.getLong(INTENT_KEY_UID) - 2000000000L,
                        input_message.text.toString()
                    )
                }
                false -> {
                    presenter?.sendMessage(
                        intent.extras.getLong(INTENT_KEY_UID),
                        null,
                        input_message.text.toString()
                    )
                }
            }
            list.add(0, Message(0, input_message.text.toString(), 0, 0,
                Date().time/1000L, 0, 2, 0, null))
            input_message.text.clear()
            messages_recycler_view.post({ adapter?.notifyItemInserted(0) })
            messages_recycler_view.scrollToPosition(0)
        }

        presenter = PresenterMessages(
            DialogDetailRepositoryImpl(
                ApiInterfaceProvider.getApiInterface(ApiDialogs::class.java)
                , applicationContext
            ),
            FriendsRepositoryImpl(
                ApiInterfaceProvider.getApiInterface(ApiFriends::class.java),
                applicationContext!!
            ),
            this
        )
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.message_menu, menu)
//        val item = menu?.findItem(R.id.menu_item_share)
//        miShareAction = MenuItemCompat.getActionProvider(item) as ShareActionProvider
//        return true
//    }

    override fun onStart() {
        super.onStart()
        presenter?.getHistory(intent.extras.getLong(INTENT_KEY_UID))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStop() {
        presenter?.clearCompositeDesposable()
        super.onStop()
    }

    override fun showMessages(messages: List<Message>) {
        val size = list.size
        list.addAll(messages)
//        adapter?.notifyItemRangeInserted(size, 30)
        adapter?.notifyDataSetChanged()
        messages_recycler_view.scrollToPosition(0)
    }

    override fun showMore(messages: List<Message>) {
        list.removeAt(list.size - 1)
        adapter?.notifyItemRemoved(list.size)
        list.addAll(messages)
        isLoading = false
        adapter?.notifyItemRangeInserted(list.size-30, 30)
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

    private fun shareMessage(message: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_chooser_title)))
    }

    companion object {

        private const val INTENT_KEY_UID: String = "DIALOG_ID"
        private const val INTENT_KEY_UNAME: String = "DIALOG_TITLE"
        private const val INTENT_KEY_CHAT: String = "IS_CHAT"

        fun getIntent(uid: Long, uname: String, isChat: Boolean, context: Context): Intent {
            val intent = Intent(context, MessagesActivity::class.java)
            intent.putExtra(INTENT_KEY_UID, uid)
            intent.putExtra(INTENT_KEY_UNAME, uname)
            intent.putExtra(INTENT_KEY_CHAT, isChat)
            return intent
        }

//        fun getExtra(activity: MessagesActivity): String {
//            return activity.intent.extras.getString(INTENT_KEY).toString()
//        }
    }
}
