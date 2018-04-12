package com.projects.vo1.customvk.data.data.api.dialogs

import com.projects.vo1.customvk.data.dialogs.DialogContainer
import com.projects.vo1.customvk.data.dialogs.details.Message
import com.projects.vo1.customvk.data.network.response.ApiResponseList
import com.projects.vo1.customvk.data.network.response.ApiResponseObject
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

    @GET("messages.send?v=5.74")
    fun sendMessage(
        @Query("access_token") token: String,
        @Query("user_id") uid: Long?,
        @Query("chat_id") cid: Long?,
        @Query("message") body: String
    ): Single<ApiResponseObject<Long>>
}

typealias ApiResponseDialogs = ApiResponseObject<ApiResponseList<DialogContainer>>
typealias ApiResponseMessages = ApiResponseObject<ApiResponseList<Message>>