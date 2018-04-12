package com.projects.vo1.customvk.ui.friends.all

import android.util.Log
import com.projects.vo1.customvk.data.data.network.Error
import com.projects.vo1.customvk.domain.friends.GetAllFriendsUseCase
import com.projects.vo1.customvk.ui.friends.FriendsView
import com.projects.vo1.customvk.ui.views.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class PresenterAllFriends(
    private val getAllFriends: GetAllFriendsUseCase,
    private val view: FriendsView
) : BasePresenter() {

    fun getFriends() {
        compositeDisposable.add(
            getAllFriends.execute(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {
                    view.showFriends(it)
                    view.hideSwipeRefresh()
                }, this::onError)
        )
    }

    fun loadMore(offset: Int) {
        compositeDisposable.add(
            getAllFriends.execute(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {
                    view.showMore(it)
                }, this::onError)
        )
    }

    private fun onError(t: Throwable) {
        view.hideSwipeRefresh()
        when (t) {
            is Error.WrongGetException -> Log.e("error: ", t.message)
            is UnknownHostException -> view.showError()
            else -> {
                Log.e("error: ", t.message + "\n")
                t.printStackTrace()
            }
        }
    }

    fun refresh() {
        clearCompositeDesposable()
        getFriends()
    }

}