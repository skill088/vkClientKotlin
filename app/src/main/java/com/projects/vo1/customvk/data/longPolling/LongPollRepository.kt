package com.projects.vo1.customvk.data.longPolling

import com.projects.vo1.customvk.services.LongPollResponse
import com.projects.vo1.customvk.services.LongPollServer
import io.reactivex.Single

interface LongPollRepository {

    fun getLongPollServer(): Single<LongPollServer>

    fun checkUpdates(url: String): Single<LongPollResponse>
}