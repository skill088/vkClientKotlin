package com.projects.vo1.customvk.domain.longPolling

import com.projects.vo1.customvk.data.data.network.Error
import com.projects.vo1.customvk.data.longPolling.LongPollResponse
import com.projects.vo1.customvk.data.longPolling.LongPollServer
import com.projects.vo1.customvk.domain.UseCaseWithoutParameters
import io.reactivex.Single
import java.net.SocketTimeoutException

class CheckUpdatesUseCase(private val longPollRepository: LongPollRepository) :
    UseCaseWithoutParameters<LongPollResponse> {

    private var url = ""

    override fun execute(): Single<LongPollResponse> {
        return when (url) {
            "" -> {
                longPollRepository.getLongPollServer()
                    .flatMap {
                        url = setUrl(it)
                        longPollRepository.checkUpdates(url)
                    }
                    .retry { t -> t is SocketTimeoutException }
                    .doOnError {
                        if (it is Error.OldTSException) {
                            url = url.dropLastWhile { it != '&' } + "ts=${it.ts}"
                            longPollRepository.checkUpdates(url)
                        }
                        if (it is Error.ObsoletedKeyException) {
                            longPollRepository.getLongPollServer()
                                .flatMap {
                                    url = setUrl(it)
                                    longPollRepository.checkUpdates(url)
                                }
                        }
                    }
            }
            else -> longPollRepository.checkUpdates(url)
                .retry { t -> t is SocketTimeoutException }
                .doOnSuccess {
                    url = url.dropLastWhile { it != '&' } + "ts=${it.ts}"
                }
        }
    }

    private fun setUrl(server: LongPollServer): String {
        return "https://${server.server}?act=a_check&wait=10&mode=2&version=2" +
                "&key=${server.key}" +
                "&ts=${server.ts}"
    }
}