package com.projects.vo1.customvk.dialogs.details

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.data.api.dialogs.ApiDialogs
import com.projects.vo1.customvk.data.dialogs.details.DialogDetailRepositoryImpl
import com.projects.vo1.customvk.data.network.ApiInterfaceProvider
import kotlinx.android.synthetic.main.fragment_messages.*

class MessagesActivity : AppCompatActivity(), MessagesView {

    private var presenter: PresenterMessages? = null
    private var adapter: AdapterMessages? = null
    private val list = mutableListOf<Message>()

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

        presenter = PresenterMessages(
            DialogDetailRepositoryImpl(ApiInterfaceProvider.getApiInterface(ApiDialogs::class.java)
                , applicationContext),
            this
        )
    }

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
        adapter?.notifyItemRangeInserted(size, 30)
    }

    companion object {
        private const val INTENT_KEY_UID: String = "DIALOG_ID"
        private const val INTENT_KEY_UNAME: String = "DIALOG_TITLE"

        fun getIntent(uid: Long, uname: String, context: Context): Intent {
            val intent = Intent(context, MessagesActivity::class.java)
            intent.putExtra(INTENT_KEY_UID, uid)
            intent.putExtra(INTENT_KEY_UNAME, uname)
            return intent
        }

//        fun getExtra(activity: MessagesActivity): String {
//            return activity.intent.extras.getString(INTENT_KEY).toString()
//        }
    }
}
