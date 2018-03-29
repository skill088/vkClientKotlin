package com.projects.vo1.customvk.data.api.dialogs

import com.projects.vo1.customvk.data.network.response.ApiResponseList
import com.projects.vo1.customvk.data.network.response.ApiResponseObject
import com.projects.vo1.customvk.dialogs.MessageContainer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiDialogs {

    @GET("messages.getDialogs?v=5.73&count=20")
    fun getDialogs(
        @Query("access_token") token: String,
        @Query("offset") offset: Int
    ): Single<ApiResponseDialogs>
}

typealias ApiResponseDialogs = ApiResponseObject<ApiResponseList<MessageContainer>>