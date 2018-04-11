package com.projects.vo1.customvk.authorization

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.activities.MainActivity
import com.projects.vo1.customvk.views.utils.SharedPrefs
import com.projects.vo1.customvk.views.utils.VkWebViewClient

class AuthorizationActivity : AppCompatActivity(), AuthorizationCallback {

    companion object {
        private const val PATH: String = "https://oauth.vk.com/authorize?client_id=6416844&display=page&redirect_uri=https://oauth.vk.com/blank.html&display=mobile&scope=70662&response_type=token&v=5.73&state=123456"
    }

    private lateinit var authView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if ((SharedPrefs()::getToken)(this) != null) {
            startActivity(MainActivity.getIntent(this))
            finish()
            return
        }

        setContentView(R.layout.activity_authorization)

        initViews()
        val vkWebClient = VkWebViewClient(this)
        authView.webViewClient = vkWebClient
        authView.loadUrl(PATH)
    }

    private fun initViews() {
       authView = findViewById(R.id.authView)
    }

    override fun authActivityCallback(token: String) {
        (SharedPrefs()::saveToken)(this, token)
        startActivity(MainActivity.getIntent(this))
    }
}
