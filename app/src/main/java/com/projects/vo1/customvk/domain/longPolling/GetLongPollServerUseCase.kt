package com.projects.vo1.customvk.domain.longPolling

import com.projects.vo1.customvk.data.longPolling.LongPollServer
import com.projects.vo1.customvk.domain.UseCaseWithoutParameters
import io.reactivex.Single

class GetLongPollServerUseCase(private val longPollRepository: LongPollRepository) :
    UseCaseWithoutParameters<LongPollServer> {

    override fun execute(): Single<LongPollServer> {
        return longPollRepository.getLongPollServer()
    }
}