package com.projects.vo1.customvk.data.dialogs.details

import android.content.Context
import android.preference.PreferenceManager
import com.projects.vo1.customvk.data.api.dialogs.ApiDialogs
import com.projects.vo1.customvk.data.api.dialogs.ApiResponseMessages
import com.projects.vo1.customvk.data.utils.Transformer.errorTransformer
import com.projects.vo1.customvk.dialogs.details.Message
import io.reactivex.Single

class DialogDetailRepositoryImpl(private val apiDialogs: ApiDialogs, private val context: Context) :
    DialogDetailRepository {

    private val token: String? = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("TOKEN", null)

    override fun getHistory(offset: Int, uid: Long): Single<List<Message>> {
        return apiDialogs.getHistory(token?: null.toString(), offset, uid)
            .compose<ApiResponseMessages>(errorTransformer())
            .map { it.response.items }
    }
}