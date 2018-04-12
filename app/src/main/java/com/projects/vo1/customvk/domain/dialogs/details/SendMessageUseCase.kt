package com.projects.vo1.customvk.domain.dialogs.details

import com.projects.vo1.customvk.domain.UseCaseWithThreeParameters
import io.reactivex.Single

class SendMessageUseCase(private val detailRepository: DialogDetailRepository) :
    UseCaseWithThreeParameters<Long?, Long?, String, Long> {

    override fun execute(param1: Long?, param2: Long?, param3: String): Single<Long> {
        return detailRepository.sendMessage(param1, param2, param3)
    }
}