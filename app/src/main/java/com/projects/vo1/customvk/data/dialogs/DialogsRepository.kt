package com.projects.vo1.customvk.data.dialogs

import com.projects.vo1.customvk.data.api.dialogs.ApiResponseDialogs
import io.reactivex.Observable
import io.reactivex.Single

interface DialogsRepository {

    fun getDialogs(offset: Int): Single<ApiResponseDialogs>
}