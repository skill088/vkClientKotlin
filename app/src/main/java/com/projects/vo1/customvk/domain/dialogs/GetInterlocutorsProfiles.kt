package com.projects.vo1.customvk.domain.dialogs

import com.projects.vo1.customvk.data.friends.FriendInfo
import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.domain.UseCase
import io.reactivex.Single

class GetInterlocutorsProfiles(private val friendsRepository: FriendsRepositoryImpl) :
    UseCase<List<Long>, List<FriendInfo>> {

    override fun execute(param: List<Long>): Single<List<FriendInfo>> {
        return friendsRepository.getUserInfos(param)
    }
}