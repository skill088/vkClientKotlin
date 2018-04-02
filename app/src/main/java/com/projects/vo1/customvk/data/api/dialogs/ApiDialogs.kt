package com.projects.vo1.customvk.data.api.dialogs

import com.projects.vo1.customvk.data.network.response.ApiResponseList
import com.projects.vo1.customvk.data.network.response.ApiResponseObject
import com.projects.vo1.customvk.dialogs.DialogContainer
import com.projects.vo1.customvk.dialogs.details.Message
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiDialogs {

    @GET("messages.getDialogs?v=5.73&count=20")
    fun getDialogs(
        @Query("access_token") token: String,
        @Query("offset") offset: Int
    ): Single<ApiResponseDialogs>

    @GET("messages.getHistory?v=5.73&count=30")
    fun getHistory(
        @Query("access_token") token: String,
        @Query("offset") offset: Int,
        @Query("user_id") uid: Long
    ): Single<ApiResponseMessages>
}

typealias ApiResponseDialogs = ApiResponseObject<ApiResponseList<DialogContainer>>
typealias ApiResponseMessages = ApiResponseObject<ApiResponseList<Message>>