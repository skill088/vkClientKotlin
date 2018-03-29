package com.projects.vo1.customvk.dialogs

import android.util.Log
import com.projects.vo1.customvk.data.dialogs.DialogsRepository
import com.projects.vo1.customvk.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PresenterDialogs(
    private val dialogsRepository: DialogsRepository,
    private val view: DialogsView
) : BasePresenter() {

    fun getDialogs() {
        compositeDisposable.add(
            dialogsRepository.getDialogs(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {},
                    {
                        Log.e("error: ", it.message)
                    }
                )
        )
    }

    fun loadMore(offset: Int) {}
}