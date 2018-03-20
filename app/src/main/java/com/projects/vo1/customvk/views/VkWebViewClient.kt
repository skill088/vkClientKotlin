package com.projects.vo1.customvk.views

import android.support.v7.app.AppCompatActivity
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.projects.vo1.customvk.views.activities.AuthorizationActivity

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Admin on 20.03.2018.
 */
class VkWebViewClient(private val activity: AppCompatActivity) : WebViewClient() {

    /* * To Evgeniy
    * У объекта request есть свойство url, но его можно достать только для API>21, у наc
    * min api = 16, поэтому пришлось заюзать депрекейтед метод, у которого
    * параметр просто стринговый url
    **/

//    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//        if (request.toString().contains("vk.com")) {
//            return false
//        }
//        Log.i("PATH: ", request.toString())
//        // все остальные ссылки будут спрашивать какой браузер открывать
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(request.toString()))
//        activity.startActivity(intent)
//        return true
//    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (url != null && url.contains("vk.com")) {
            return false
        }
        Log.i("PATH: ", url)
        // все остальные ссылки будут спрашивать какой браузер открывать
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
        activity.startActivity(intent)
        return true
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        if (url?.contains("https://oauth.vk.com/blank.html#") == true) {
            Toast.makeText(activity, "SUCCESS", Toast.LENGTH_LONG).show()

            (SharedPrefs()::saveToken)(activity, url.substring(url.indexOf("=")+1 until
                    url.indexOf("&")))

            activity.startActivity(AuthorizationActivity.getIntent(activity))
        }
    }
}