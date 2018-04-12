package com.projects.vo1.customvk.ui.friends.online

import android.util.Log
import com.projects.vo1.customvk.data.data.network.Error
import com.projects.vo1.customvk.data.friends.FriendInfo
import com.projects.vo1.customvk.domain.friends.GetOnlineFriendsUseCase
import com.projects.vo1.customvk.ui.friends.FriendsView
import com.projects.vo1.customvk.ui.views.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class PresenterFriendsOnline(
    private val getOnlineFriends: GetOnlineFriendsUseCase,
    private val view: FriendsView
) : BasePresenter() {

    fun getOnlineFriends() {
        compositeDisposable.add(
            getOnlineFriends.execute(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError)
        )
    }

    fun loadMore(offset: Int) {
        compositeDisposable.add(
            getOnlineFriends.execute(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessLoadMore, this::onError)
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

    private fun onSuccess(list: List<FriendInfo>) {
        view.showFriends(list)
        view.hideSwipeRefresh()
    }

    private fun onSuccessLoadMore(list: List<FriendInfo>) {
        view.showMore(list)
    }

    fun refresh() {
        clearCompositeDesposable()
        getOnlineFriends()
    }
}
