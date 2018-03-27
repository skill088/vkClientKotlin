package com.projects.vo1.customvk.data.api.friends

import com.projects.vo1.customvk.friends.FriendInfo
import com.projects.vo1.customvk.friends.FriendsIds
import com.projects.vo1.customvk.data.nework.model_api_response.ApiResponseList
import com.projects.vo1.customvk.data.nework.model_api_response.ApiResponseObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiFriends {

    @GET("friends.get?order=name&count=10&fields=photo_100&v=5.73")
    fun getFriends(@Query("access_token") token: String,
                   @Query("offset") offset: Int): Observable<ApiResponseFriendsAll>

    @GET("friends.getOnline?order=hints&v=5.73&count=10")
    fun getOnline(@Query("access_token") token: String,
                  @Query("offset") offset: Int): Observable<FriendsIds>

    @GET("users.get?fields=photo_100&v=5.73")
    fun getInfoOnline(@Query("access_token") token: String,
                      @Query("user_ids") ids: String): Observable<ApiResponseFriendsOnline>

}

typealias ApiResponseFriendsAll = ApiResponseObject<ApiResponseList<FriendInfo>>
typealias ApiResponseFriendsOnline = ApiResponseObject<List<FriendInfo>>