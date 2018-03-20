package com.projects.vo1.customvk.views.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.views.VkWebViewClient

class AuthorizationActivity : AppCompatActivity() {

    companion object {
        private val PATH: String = "https://oauth.vk.com/authorize?client_id=6416844&display=page&redirect_uri=https://oauth.vk.com/blank.html&display=mobile&scope=70662&response_type=token&v=5.73&state=123456"
        fun getIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
//            intent.putExtra(INTENT_KEY, param)
            return intent
        }
    }

    lateinit private var authView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        initViews()
        val vkWebClient = VkWebViewClient(this)
        authView.webViewClient = vkWebClient
        authView.loadUrl(PATH)
    }

    private fun initViews() {
       authView = findViewById(R.id.authView)
    }
}
