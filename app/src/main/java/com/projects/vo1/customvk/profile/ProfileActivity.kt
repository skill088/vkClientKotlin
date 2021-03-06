package com.projects.vo1.customvk.profile

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.projects.vo1.customvk.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                FragmentProfile.newInstance(getExtra(this)),
                "ProfileInfo"
            )
            .commit()
    }

    companion object {
        private const val INTENT_KEY: String = "USER_ID"

        fun getIntent(param: String?, context: Context): Intent {
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra(INTENT_KEY, param)
            return intent
        }

        fun getExtra(activity: ProfileActivity): String {
            return activity.intent.extras.getString(INTENT_KEY).toString()
        }
    }
}
