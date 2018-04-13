package com.projects.vo1.customvk.domain.dialogs.details

import com.projects.vo1.customvk.data.dialogs.details.Message
import com.projects.vo1.customvk.data.friends.FriendInfo
import com.projects.vo1.customvk.domain.UseCase
import com.projects.vo1.customvk.domain.friends.FriendsRepository
import io.reactivex.Single

class GetHistoryUseCase(private val detailRepository: DialogDetailRepository,
                        private val friendsRepository: FriendsRepository) :
    UseCase<GetHistoryUseCase.Parameter, List<Message>> {

    class Parameter(val offset: Int, val uid: Long)

    override fun execute(param: Parameter): Single<List<Message>> {
        return detailRepository.getHistory(param.offset, param.uid)
            .flatMap { list ->
                friendsRepository.getUserInfos(
                    list.map { it.userId })
                    .doOnSuccess { t ->
                        bindUsersToDialogs(list, t)
                    }
                    .map { list }
            }
    }

    private fun bindUsersToDialogs(list: List<Message>, profilesList: List<FriendInfo>) {
        list.forEach { t ->
            t.photo = profilesList.find { it.id == t.userId }?.photo
        }
    }
}