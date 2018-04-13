package com.projects.vo1.customvk.domain.dialogs.details

import com.projects.vo1.customvk.domain.UseCase
import io.reactivex.Single

class SendMessageUseCase(private val detailRepository: DialogDetailRepository) :
    UseCase<SendMessageUseCase.Parameter, Long> {

    class Parameter(val uid: Long?, val cid: Long?, val body: String)

    override fun execute(param: Parameter): Single<Long> {
        return detailRepository.sendMessage(param.uid, param.cid, param.body)
    }
}