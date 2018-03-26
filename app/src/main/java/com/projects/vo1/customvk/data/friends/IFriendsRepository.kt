package com.projects.vo1.customvk.data.friends

import com.projects.vo1.customvk.friends.FriendInfo
import com.projects.vo1.customvk.friends.FriendsIds
import com.projects.vo1.customvk.model.ApiResponseList
import com.projects.vo1.customvk.model.ApiResponseObject
import io.reactivex.Observable

interface IFriendsRepository {

    fun getAll(offset: Int): Observable<ApiResponseFriendsAll>
    fun getOnline(): Observable<FriendsIds>
    fun getOnlineInfo(ids: String): Observable<ApiResponseObject<List<FriendInfo>>>
    fun getAllInfo(ids: String): Observable<ApiResponseFriendsAll>
}

typealias ApiResponseFriendsAll = ApiResponseObject<ApiResponseList<FriendInfo>>