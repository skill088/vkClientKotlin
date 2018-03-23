package com.projects.vo1.customvk.presenter

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Admin on 23.03.2018.
 */
open class BasePresenter {

    protected var compositeDisposable = CompositeDisposable()

    fun clearCompositeDesposable() {
        compositeDisposable.clear()
    }
}