package com.projects.vo1.customvk.domain.friends

import com.projects.vo1.customvk.data.friends.FriendInfo
import com.projects.vo1.customvk.data.network.response.ApiResponseObject
import io.reactivex.Single

interface FriendsRepository {

    fun getAll(offset: Int): Single<List<FriendInfo>>
    fun getOnlineFriends(offset: Int): Single<ApiResponseObject<LongArray>>
    fun getUserInfos(ids: List<Long>): Single<List<FriendInfo>>
}

