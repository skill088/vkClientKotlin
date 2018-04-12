package com.projects.vo1.customvk.domain.longPolling

import com.projects.vo1.customvk.data.longPolling.LongPollResponse
import com.projects.vo1.customvk.domain.UseCase
import io.reactivex.Single

class CheckUpdatesUseCase(private val longPollRepository: LongPollRepository) :
    UseCase<String, LongPollResponse> {

    override fun execute(param: String): Single<LongPollResponse> {
        return longPollRepository.checkUpdates(param)
    }
}