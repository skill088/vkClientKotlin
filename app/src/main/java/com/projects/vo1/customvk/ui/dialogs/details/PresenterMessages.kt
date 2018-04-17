package com.projects.vo1.customvk.ui.dialogs.details

import android.util.Log
import com.projects.vo1.customvk.data.dialogs.details.Message
import com.projects.vo1.customvk.data.longPolling.MessageNotification
import com.projects.vo1.customvk.domain.dialogs.details.GetHistoryUseCase
import com.projects.vo1.customvk.domain.dialogs.details.SendMessageUseCase
import com.projects.vo1.customvk.domain.longPolling.CheckUpdatesUseCase
import com.projects.vo1.customvk.ui.views.presenter.BasePresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PresenterMessages(
    private val getHistory: GetHistoryUseCase,
    private val sendMessage: SendMessageUseCase,
    private val checkLongPollUpdates: CheckUpdatesUseCase,
    private val view: MessagesView
) : BasePresenter() {

    private var url = ""

    fun getHistory(uid: Long) {
        compositeDisposable.add(
            doHistoryQuery(0, uid)
                .subscribe(
                    {
                        view.showMessages(it)
                    }, this::onError
                )
        )
    }

    fun loadMore(offset: Int, uid: Long) {
        compositeDisposable.add(
            doHistoryQuery(offset, uid)
                .subscribe(
                    {
                        view.showMore(it)
                    }, this::onError
                )
        )
    }

    fun sendMessage(uid: Long?, cid: Long?, body: String) {
        compositeDisposable.add(
            sendMessage.execute(SendMessageUseCase.Parameter(uid, cid, body))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.setSent(it ?: -1)
                    }, this::onError
                )
        )
    }

    fun checkUpdates() {
        compositeDisposable.add(
            checkLongPollUpdates.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it.updates.find { it[0] == 4.00 } != null) {
                            setNewestData(it.updates.find { it[0] == 4.00 }!!)
                        }
                        checkUpdates()
                    }, {
                        Log.e("PresenterMessages", it.message)
                        checkUpdates()
                    }
                )
        )
    }

    private fun setNewestData(list: List<Any>) {
        val msg = MessageNotification(
            (list[1] as Double).toLong(),
            (list[3] as Double).toLong(),
            (list[4] as Double).toLong(),
            list[5].toString()
        )
        view.setNewestData(msg)
    }

    private fun onError(t: Throwable) {
        Log.e("Network error", t.message)
    }

    private fun doHistoryQuery(offset: Int, uid: Long): Single<List<Message>> {
        return getHistory.execute(GetHistoryUseCase.Parameter(offset, uid))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}