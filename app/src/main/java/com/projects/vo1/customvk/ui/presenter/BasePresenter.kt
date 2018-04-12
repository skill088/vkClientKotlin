package com.projects.vo1.customvk.ui.views.presenter

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter {

    protected var compositeDisposable = CompositeDisposable()

    fun clearCompositeDesposable() {
        compositeDisposable.clear()
    }
}