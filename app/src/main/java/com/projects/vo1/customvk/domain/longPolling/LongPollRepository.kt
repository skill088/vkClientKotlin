package com.projects.vo1.customvk.domain.longPolling

import com.projects.vo1.customvk.data.longPolling.LongPollResponse
import com.projects.vo1.customvk.data.longPolling.LongPollServer
import io.reactivex.Single

interface LongPollRepository {

    fun getLongPollServer(): Single<LongPollServer>

    fun checkUpdates(url: String): Single<LongPollResponse>
}