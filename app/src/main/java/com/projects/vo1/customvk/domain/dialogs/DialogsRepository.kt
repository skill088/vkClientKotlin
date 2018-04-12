package com.projects.vo1.customvk.domain.dialogs

import com.projects.vo1.customvk.data.dialogs.DialogContainer
import com.projects.vo1.customvk.data.dialogs.details.Message
import io.reactivex.Single

interface DialogsRepository {

    fun getDialogs(offset: Int): Single<List<DialogContainer>>

    fun getHistory(offset: Int, uid: Long): Single<List<Message>>

}