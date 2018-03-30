package com.projects.vo1.customvk.data.dialogs

import com.projects.vo1.customvk.data.api.dialogs.ApiResponseDialogs
import com.projects.vo1.customvk.data.api.dialogs.ApiResponseMessages
import io.reactivex.Single

interface DialogsRepository {

    fun getDialogs(offset: Int): Single<ApiResponseDialogs>

    fun getHistory(offset: Int): Single<ApiResponseMessages>
}