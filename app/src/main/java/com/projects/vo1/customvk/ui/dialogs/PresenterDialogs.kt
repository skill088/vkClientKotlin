package com.projects.vo1.customvk.ui.dialogs

import android.util.Log
import com.projects.vo1.customvk.data.longPolling.LongPollRepositoryImpl
import com.projects.vo1.customvk.domain.dialogs.GetDialogsUseCase
import com.projects.vo1.customvk.ui.views.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PresenterDialogs(
    private val getDialogs: GetDialogsUseCase,
    private val longPollRepository: LongPollRepositoryImpl,
    private val view: DialogsView
) : BasePresenter() {

    private var dispose: Disposable? = null

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

    fun subscribe() {
        dispose = longPollRepository.subscribeToUpdates()
                .subscribe(
                    {
                        view.setNewestData(it)
                    },
                    {}
                )
    }

    fun unsubscribe() {
        dispose?.dispose()
    }

    private fun onError(t: Throwable) {
        Log.e("error: ", t.message)
    }

}