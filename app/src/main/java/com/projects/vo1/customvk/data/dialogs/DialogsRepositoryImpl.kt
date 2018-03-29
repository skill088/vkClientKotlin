package com.projects.vo1.customvk.data.dialogs

import android.content.Context
import android.preference.PreferenceManager
import com.projects.vo1.customvk.data.api.dialogs.ApiDialogs
import com.projects.vo1.customvk.data.api.dialogs.ApiResponseDialogs
import io.reactivex.Single

class DialogsRepositoryImpl(private val apiDialogs: ApiDialogs, val context: Context) :
    DialogsRepository {

    private val token: String? = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("TOKEN", null)

    override fun getDialogs(offset: Int): Single<ApiResponseDialogs> {
        return apiDialogs.getDialogs(token?: null.toString(), offset)
    }
}