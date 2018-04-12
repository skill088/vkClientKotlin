package com.projects.vo1.customvk.domain.dialogs.details

import com.projects.vo1.customvk.data.dialogs.details.Message
import io.reactivex.Single

interface DialogDetailRepository {

    fun getHistory(offset: Int, uid: Long): Single<List<Message>>

    fun sendMessage(uid: Long?, cid: Long?, body: String): Single<Long>
}