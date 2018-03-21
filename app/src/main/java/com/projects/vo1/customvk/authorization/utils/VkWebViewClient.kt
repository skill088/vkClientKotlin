package com.projects.vo1.customvk.views.utils

import android.webkit.WebView
import android.webkit.WebViewClient
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log

class VkWebViewClient(private val callback: AuthorizationCallback) : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        if (url?.contains("https://oauth.vk.com/blank.html#") == true) {
            val token = Uri.parse(url.replace("#", "?"))
                    .getQueryParameter("access_token")
            callback.authActivityCallback(token)
        }
    }
}