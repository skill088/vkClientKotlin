package com.projects.vo1.customvk.dialogs.details

import com.projects.vo1.customvk.data.dialogs.details.DialogDetailRepositoryImpl
import com.projects.vo1.customvk.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PresenterMessages(private val dialogsDeatailRep: DialogDetailRepositoryImpl,
                        private val view: MessagesView) : BasePresenter() {

    fun getHistory(uid: Long) {
        compositeDisposable.add(
            dialogsDeatailRep.getHistory(0, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.showMessages(it)
                    },
                    {}
                )
        )
    }
}