package com.projects.vo1.customvk.data.dialogs

import android.content.Context
import android.preference.PreferenceManager
import com.projects.vo1.customvk.data.api.dialogs.ApiDialogs
import com.projects.vo1.customvk.data.api.dialogs.ApiResponseDialogs
import com.projects.vo1.customvk.data.api.dialogs.ApiResponseMessages
import com.projects.vo1.customvk.data.utils.Transformer.errorTransformer
import io.reactivex.Single

class DialogsRepositoryImpl(private val apiDialogs: ApiDialogs, val context: Context) :
    DialogsRepository {

    private val token: String? = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("TOKEN", null)

    override fun getDialogs(offset: Int): Single<ApiResponseDialogs> {
        return apiDialogs.getDialogs(token?: null.toString(), offset)
            .compose(errorTransformer())
    }

    override fun getHistory(offset: Int): Single<ApiResponseMessages> {
        return apiDialogs.getHistory(token?: null.toString(), offset)
            .compose(errorTransformer())
    }
}