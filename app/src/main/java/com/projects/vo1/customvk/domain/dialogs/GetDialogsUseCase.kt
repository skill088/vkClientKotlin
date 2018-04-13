package com.projects.vo1.customvk.domain.dialogs

import com.projects.vo1.customvk.data.dialogs.Dialog
import com.projects.vo1.customvk.data.dialogs.DialogContainer
import com.projects.vo1.customvk.data.friends.FriendInfo
import com.projects.vo1.customvk.domain.UseCase
import com.projects.vo1.customvk.domain.friends.FriendsRepository
import io.reactivex.Single

class GetDialogsUseCase(private val dialogsRepository: DialogsRepository,
                        private val friendsRepository: FriendsRepository) :
    UseCase<Int, List<Dialog>> {

    override fun execute(param: Int): Single<List<Dialog>> {
        return dialogsRepository.getDialogs(param).flatMap { list ->
            friendsRepository.getUserInfos(
                list.map { it.dialog.userId })
                .doOnSuccess { t ->
                    bindUsersToDialogs(list, t)
                }
                .map { list.map { it.dialog } }
        }
    }

    private fun bindUsersToDialogs(list: List<DialogContainer>, profilesList: List<FriendInfo>) {
        list.forEach { t ->
            t.dialog.userName = profilesList.find { it.id == t.dialog.userId }.toString()
            t.dialog.userPhoto =
                    if (t.dialog.out != 1) profilesList.find { it.id == t.dialog.userId }?.photo
                    else profilesList.find { it.id == 37374876L }?.photo
            if (t.dialog.photo == null)
                t.dialog.photo = profilesList.find { it.id == t.dialog.userId }?.photo
        }
    }
}