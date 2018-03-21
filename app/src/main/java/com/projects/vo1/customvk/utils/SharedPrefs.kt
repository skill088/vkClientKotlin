package com.projects.vo1.customvk.views.utils

import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager



/**
 * Created by Admin on 20.03.2018.
 */
class SharedPrefs {
    private val TOKEN = "TOKEN"

    private fun getInstance(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun getToken(context: Context): String? {
        return getInstance(context).getString(TOKEN, null)
    }

    fun saveToken(context: Context, token: String) {
        val edit = getInstance(context).edit()
        edit.putString(TOKEN, token)
        edit.apply()
    }
}