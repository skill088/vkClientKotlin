package com.projects.vo1.customvk.domain.friends

import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.data.friends.FriendInfo
import com.projects.vo1.customvk.domain.UseCase
import io.reactivex.Single

class GetAllFriendsUseCase(private val friendsRepository: FriendsRepositoryImpl) :
    UseCase<Int, List<FriendInfo>> {

    override fun execute(param: Int): Single<List<FriendInfo>> {
        return friendsRepository.getAll(param)
    }
}