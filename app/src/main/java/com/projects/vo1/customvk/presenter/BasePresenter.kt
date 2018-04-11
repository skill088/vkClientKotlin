package com.projects.vo1.customvk.presenter

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter {

    protected var compositeDisposable = CompositeDisposable()

    fun clearCompositeDesposable() {
        compositeDisposable.clear()
    }
}