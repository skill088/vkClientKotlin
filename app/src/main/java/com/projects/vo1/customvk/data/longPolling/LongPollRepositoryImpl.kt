package com.projects.vo1.customvk.data.longPolling

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import com.projects.vo1.customvk.data.data.api.longPolling.ApiLongPolling
import com.projects.vo1.customvk.data.data.utils.Transformer.errorTransformer
import com.projects.vo1.customvk.data.data.utils.Transformer.longPollTransformer
import com.projects.vo1.customvk.data.device.services.DialogsService
import com.projects.vo1.customvk.data.device.services.DialogsService.Companion.intentFilter
import com.projects.vo1.customvk.data.network.response.ApiResponseObject
import com.projects.vo1.customvk.domain.longPolling.LongPollRepository
import io.reactivex.Observable
import io.reactivex.Single

class LongPollRepositoryImpl(private val apiLongPolling: ApiLongPolling, val context: Context) :
    LongPollRepository {

    interface Listener {
        fun method(param: MessageNotification)
    }

    private var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val message =
                intent?.getParcelableExtra<MessageNotification>(DialogsService.MESSAGE_NOTIFICATION)!!
            observers.forEach {
                it.method(message)
            }
        }
    }


    private val token: String? = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("TOKEN", null)

    override fun subscribeToUpdates() = Observable.create<MessageNotification> {
        if (observers.size == 0) {
            context.registerReceiver(broadcastReceiver, intentFilter)
        }
        val listener = object : Listener {
            override fun method(param: MessageNotification) {
                it.onNext(param)
            }
        }
        observers.add(listener)
        it.setCancellable {
            observers.remove(listener)
            if (observers.size == 0)
                context.unregisterReceiver(broadcastReceiver)
        }
    }

    override fun getLongPollServer(): Single<LongPollServer> {
        return apiLongPolling.getLongPollServer(token ?: null.toString())
            .compose(errorTransformer<ApiResponseObject<LongPollServer>>())
            .map { it.response }
    }

    override fun checkUpdates(url: String): Single<LongPollResponse> {
        return apiLongPolling.checkUpdates(url)
            .compose(longPollTransformer())
    }

    companion object {
        val observers = mutableListOf<Listener>()
    }
}