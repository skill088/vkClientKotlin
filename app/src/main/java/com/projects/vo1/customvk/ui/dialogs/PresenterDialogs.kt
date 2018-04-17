package com.projects.vo1.customvk.ui.dialogs

import android.util.Log
import com.projects.vo1.customvk.data.longPolling.MessageNotification
import com.projects.vo1.customvk.domain.dialogs.GetDialogsUseCase
import com.projects.vo1.customvk.domain.longPolling.CheckUpdatesUseCase
import com.projects.vo1.customvk.ui.views.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PresenterDialogs(
    private val getDialogs: GetDialogsUseCase,
    private val checkLongPollUpdates: CheckUpdatesUseCase,
    private val view: DialogsView
) : BasePresenter() {

    fun getDialogs() {
        compositeDisposable.add(
            getDialogs.execute(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.showMessages(it)
                    },
                    this::onError
                )
        )
    }

    fun loadMore(offset: Int) {
        compositeDisposable.add(
            getDialogs.execute(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.showMore(it)
                    },
                    this::onError
                )
        )
    }

    fun reload() {
        compositeDisposable.add(
            getDialogs.execute(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.reload(it)
                    },
                    this::onError
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
                        val temp = it.updates.find { it[0] == 4.00 }
                        if (temp != null) {
                            setNewestData(temp)
                        }
                        checkUpdates()
                    }, {
                        Log.e("PresenterDialogs", it.message)
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
        Log.e("error: ", t.message)
    }

}