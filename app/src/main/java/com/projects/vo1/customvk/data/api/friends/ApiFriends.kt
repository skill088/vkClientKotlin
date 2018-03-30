package com.projects.vo1.customvk.data.api.friends

import com.projects.vo1.customvk.friends.FriendInfo
import com.projects.vo1.customvk.data.network.response.ApiResponseList
import com.projects.vo1.customvk.data.network.response.ApiResponseObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiFriends {

    @GET("friends.get?order=name&count=10&fields=photo_100&v=5.73")
    fun getFriends(
        @Query("access_token") token: String,
        @Query("offset") offset: Int
    ): Single<ApiResponseFriendsAll>

    @GET("friends.getOnlineFriends?order=hints&v=5.73&count=10")
    fun getOnline(
        @Query("access_token") token: String,
        @Query("offset") offset: Int
    ): Single<ApiResponseObject<LongArray>>

    @GET("users.get?fields=photo_100&v=5.73")
    fun getInfoOnline(
        @Query("access_token") token: String,
        @Query("user_ids") ids: String
    ): Single<ApiResponseFriendsOnline>

}

typealias ApiResponseFriendsAll = ApiResponseObject<ApiResponseList<FriendInfo>>
typealias ApiResponseFriendsOnline = ApiResponseObject<List<FriendInfo>>