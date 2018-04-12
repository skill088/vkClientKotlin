package com.projects.vo1.customvk.data.data.api.longPolling

import com.projects.vo1.customvk.data.network.response.ApiResponseObject
import com.projects.vo1.customvk.data.longPolling.LongPollResponse
import com.projects.vo1.customvk.data.longPolling.LongPollServer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiLongPolling {

    @GET("messages.getLongPollServer?v=5.74&need_pts=0&lp_version=3")
    fun getLongPollServer(@Query("access_token") token: String):
            Single<ApiResponseObject<LongPollServer>>

    @GET
    fun checkUpdates(@Url url: String): Single<LongPollResponse>
}