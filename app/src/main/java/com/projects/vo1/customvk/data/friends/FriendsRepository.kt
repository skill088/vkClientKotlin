package com.projects.vo1.customvk.data.friends

import com.projects.vo1.customvk.data.api.friends.ApiResponseFriendsAll
import com.projects.vo1.customvk.friends.FriendInfo
import com.projects.vo1.customvk.data.network.response.ApiResponseObject
import io.reactivex.Single

interface FriendsRepository {

    fun getAll(offset: Int): Single<ApiResponseFriendsAll>
    fun getOnlineFriends(offset: Int): Single<ApiResponseObject<LongArray>>
    fun getUserInfos(ids: List<Long>): Single<ApiResponseObject<List<FriendInfo>>>
}

