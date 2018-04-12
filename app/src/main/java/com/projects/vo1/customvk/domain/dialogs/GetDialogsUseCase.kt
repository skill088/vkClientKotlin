package com.projects.vo1.customvk.domain.dialogs

import com.projects.vo1.customvk.data.dialogs.DialogContainer
import com.projects.vo1.customvk.domain.UseCase
import io.reactivex.Single

class GetDialogsUseCase(private val dialogsRepository: DialogsRepository) :
    UseCase<Int, List<DialogContainer>> {

    override fun execute(param: Int): Single<List<DialogContainer>> {
        return dialogsRepository.getDialogs(param)
    }
}