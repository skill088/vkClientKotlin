package com.projects.vo1.customvk.data.data.dialogs

import android.content.Context
import android.preference.PreferenceManager
import com.projects.vo1.customvk.data.data.api.dialogs.ApiDialogs
import com.projects.vo1.customvk.data.data.api.dialogs.ApiResponseDialogs
import com.projects.vo1.customvk.data.data.api.dialogs.ApiResponseMessages
import com.projects.vo1.customvk.data.data.utils.Transformer.errorTransformer
import com.projects.vo1.customvk.data.dialogs.DialogContainer
import com.projects.vo1.customvk.data.dialogs.details.Message
import com.projects.vo1.customvk.domain.dialogs.DialogsRepository
import io.reactivex.Single

class DialogsRepositoryImpl(private val apiDialogs: ApiDialogs, val context: Context) :
    DialogsRepository {

    private val token: String? = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("TOKEN", null)

    override fun getDialogs(offset: Int): Single<List<DialogContainer>> {
        return apiDialogs.getDialogs(token?: null.toString(), offset)
            .compose(errorTransformer<ApiResponseDialogs>())
            .map { it.response!!.items }
    }

    override fun getHistory(offset: Int, uid: Long): Single<List<Message>> {
        return apiDialogs.getHistory(token?: null.toString(), offset, uid)
            .compose(errorTransformer<ApiResponseMessages>())
            .map { it.response!!.items }
    }

}