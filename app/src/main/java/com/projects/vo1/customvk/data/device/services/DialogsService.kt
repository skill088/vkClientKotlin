package com.projects.vo1.customvk.data.device.services

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import com.projects.vo1.customvk.data.data.api.longPolling.ApiLongPolling
import com.projects.vo1.customvk.data.data.network.ApiInterfaceProvider
import com.projects.vo1.customvk.data.data.network.Error
import com.projects.vo1.customvk.data.longPolling.LongPollRepositoryImpl
import com.projects.vo1.customvk.data.longPolling.MessageNotification
import com.projects.vo1.customvk.domain.longPolling.CheckUpdatesUseCase
import com.projects.vo1.customvk.domain.longPolling.GetLongPollServerUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.SocketTimeoutException


class DialogsService : Service() {

    private val TAG: String = "DialogsService"

    private val compositeDisposable = CompositeDisposable()
    private lateinit var getLongPollServer: GetLongPollServerUseCase
    private lateinit var checkUpdates: CheckUpdatesUseCase
    private var url = ""

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        getLongPollServer =
                GetLongPollServerUseCase(
                    LongPollRepositoryImpl(
                        ApiInterfaceProvider.getApiInterface(
                            ApiLongPolling::class.java
                        ),
                        this
                    )
                )
        checkUpdates =
                CheckUpdatesUseCase(
                    LongPollRepositoryImpl(
                        ApiInterfaceProvider.getApiInterface(
                            ApiLongPolling::class.java
                        ),
                        this
                    )
                )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        compositeDisposable.add(
            getLongPollServer.execute()
                .flatMap {
                    url = "https://${it.server}?act=a_check&wait=10&mode=2&version=2" +
                            "&key=${it.key}" +
                            "&ts=${it.ts}"
                    checkUpdates.execute(url)
                        .retry { t -> t is SocketTimeoutException }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it.updates.find { it[0] == 4.00 } != null) {
                            sendMessageToActivity(it.updates.find { it[0] == 4.00 }!!)
                        }
                        checkChanges(url.dropLastWhile { it != '&' } + "ts=${it.ts}")
                    }, {
                        Log.e(TAG, it.message)
                        if (it is Error.OldTSException)
                            checkChanges(url.dropLastWhile { it != '&' } + "ts=${it.ts}")
                    })
        )
        return super.onStartCommand(intent, flags, startId)
    }

    private fun checkChanges(query: String) {
        compositeDisposable.add(
            checkUpdates.execute(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry { t -> t is SocketTimeoutException }
                .subscribe({
                    if (it.updates.find { it[0] == 4.00 } != null) {
                        sendMessageToActivity(it.updates.find { it[0] == 4.00 }!!)
                    }
                    checkChanges(query.dropLastWhile { it != '&' } + "ts=${it.ts}")
                }, {
                    Log.e(TAG, it.message)
                    if (it is Error.OldTSException)
                        checkChanges(query.dropLastWhile { it != '&' } + "ts=${it.ts}")
                    if (it is Error.ObsoletedKeyException) {
                        fixLongPollingData()
                        checkChanges(url)
                    }
                })
        )
    }

    private fun sendMessageToActivity(list: List<Any>) {
        val intent = Intent(BROADCAST_ACTION)
        val msg = MessageNotification(
            (list[1] as Double).toLong(),
            (list[3] as Double).toLong(),
            (list[4] as Double).toLong(),
            list[5].toString()
        )
        intent.putExtra(MESSAGE_NOTIFICATION, msg)
        sendBroadcast(intent)
    }

    private fun fixLongPollingData() {
        compositeDisposable.add(
            getLongPollServer.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        url = "https://${it.server}?act=a_check&wait=10&mode=2&version=2" +
                                "&key=${it.key}" +
                                "&ts=${it.ts}"
                    }, {})
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    companion object {
        const val MESSAGE_NOTIFICATION = "MESSAGE_NOTIFICATION"
        private const val BROADCAST_ACTION = "com.projects.vo1.customvk.caUI.activities.MainActivity"

        val intentFilter = IntentFilter(BROADCAST_ACTION)
    }
}
