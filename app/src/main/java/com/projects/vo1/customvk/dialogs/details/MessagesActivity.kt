package com.projects.vo1.customvk.dialogs.details

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.projects.vo1.customvk.R

class MessagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    companion object {
        private const val INTENT_KEY: String = "DIALOG_ID"

        fun getIntent(param: Int, context: Context): Intent {
            val intent = Intent(context, MessagesActivity::class.java)
            intent.putExtra(INTENT_KEY, param)
            return intent
        }

        fun getExtra(activity: MessagesActivity): String {
            return activity.intent.extras.getString(INTENT_KEY).toString()
        }
    }
}
