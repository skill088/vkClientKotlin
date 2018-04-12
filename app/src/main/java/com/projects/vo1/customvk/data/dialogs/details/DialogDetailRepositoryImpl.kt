package com.projects.vo1.customvk.data.data.dialogs.details

import android.content.Context
import android.preference.PreferenceManager
import com.projects.vo1.customvk.data.data.api.dialogs.ApiDialogs
import com.projects.vo1.customvk.data.data.api.dialogs.ApiResponseMessages
import com.projects.vo1.customvk.data.data.utils.Transformer.errorTransformer
import com.projects.vo1.customvk.data.dialogs.details.Message
import com.projects.vo1.customvk.data.network.response.ApiResponseObject
import com.projects.vo1.customvk.domain.dialogs.details.DialogDetailRepository
import io.reactivex.Single

class DialogDetailRepositoryImpl(private val apiDialogs: ApiDialogs, private val context: Context) :
    DialogDetailRepository {

    private val token: String? = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("TOKEN", null)

    override fun getHistory(offset: Int, uid: Long): Single<List<Message>> {
        return apiDialogs.getHistory(token?: null.toString(), offset, uid)
            .compose<ApiResponseMessages>(errorTransformer())
            .map { it.response?.items }
    }

    override fun sendMessage(uid: Long?, cid: Long?, body: String): Single<Long> {
        return apiDialogs.sendMessage(token?: null.toString(), uid, cid, body)
            .compose(errorTransformer<ApiResponseObject<Long>>())
            .map { it.response }
    }
}