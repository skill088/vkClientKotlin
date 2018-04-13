package com.projects.vo1.customvk.ui.dialogs.details

import android.util.Log
import com.projects.vo1.customvk.data.dialogs.details.Message
import com.projects.vo1.customvk.domain.dialogs.details.GetHistoryUseCase
import com.projects.vo1.customvk.domain.dialogs.details.SendMessageUseCase
import com.projects.vo1.customvk.ui.views.presenter.BasePresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PresenterMessages(private val getHistory: GetHistoryUseCase,
                        private val sendMessage: SendMessageUseCase,
                        private val view: MessagesView
) : BasePresenter() {

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
                        view.setSent(it?:-1)
                    }, this::onError
                )
        )
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