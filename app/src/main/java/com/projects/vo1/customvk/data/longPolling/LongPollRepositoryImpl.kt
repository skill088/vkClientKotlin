package com.projects.vo1.customvk.data.longPolling

import android.content.Context
import android.preference.PreferenceManager
import com.projects.vo1.customvk.data.api.longPolling.ApiLongPolling
import com.projects.vo1.customvk.data.network.response.ApiResponseObject
import com.projects.vo1.customvk.data.utils.Transformer.errorTransformer
import com.projects.vo1.customvk.data.utils.Transformer.longPollTransformer
import com.projects.vo1.customvk.services.LongPollResponse
import com.projects.vo1.customvk.services.LongPollServer
import io.reactivex.Single

class LongPollRepositoryImpl(private val apiLongPolling: ApiLongPolling, val context: Context) :
    LongPollRepository {

    private val token: String? = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("TOKEN", null)


    override fun getLongPollServer(): Single<LongPollServer> {
        return apiLongPolling.getLongPollServer(token?: null.toString())
            .compose(errorTransformer<ApiResponseObject<LongPollServer>>())
            .map { it.response }
    }

    override fun checkUpdates(url: String): Single<LongPollResponse> {
        return apiLongPolling.checkUpdates(url)
            .compose(longPollTransformer())
    }
}