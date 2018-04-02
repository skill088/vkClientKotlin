package com.projects.vo1.customvk.data.dialogs.details

import com.projects.vo1.customvk.data.network.response.ApiResponseObject
import com.projects.vo1.customvk.dialogs.details.Message
import io.reactivex.Single

interface DialogDetailRepository {

    fun getHistory(offset: Int, uid: Long): Single<List<Message>>

    fun sendMessage(uid: Long?, cid: Long?, body: String): Single<ApiResponseObject<Long>>
}