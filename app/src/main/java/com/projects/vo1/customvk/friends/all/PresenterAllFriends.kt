package com.projects.vo1.customvk.friends.all

import android.util.Log
import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.data.network.Error
import com.projects.vo1.customvk.friends.FriendInfo
import com.projects.vo1.customvk.friends.FriendsView
import com.projects.vo1.customvk.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class PresenterAllFriends(
    private val friendsRepository: FriendsRepositoryImpl,
    private val view: FriendsView
) : BasePresenter() {

    fun getFriends() {
        compositeDisposable.add(
            friendsRepository.getAll(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.showFriends(it.response.items)
                        view.hideSwipeRefresh()
                    },
                    { t: Throwable? ->
                        view.hideSwipeRefresh()
                        when (t) {
                            is Error.WrongGetException -> Log.e("error: ", t.message)
                            is UnknownHostException -> view.showError()
                            else -> {
                                Log.e("error: ", t?.message + "\n")
                                t?.printStackTrace()
                            }
                        }
                    }
                )
        )
    }

    fun loadMore(offset: Int) {
        friendsRepository.getAll(offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.showMore(it.response.items)
                },
                { t: Throwable? ->
                    view.hideSwipeRefresh()
                    when (t) {
                        is Error.WrongGetException -> Log.e("error: ", t.message)
                        is UnknownHostException -> view.showError()
                        else -> {
                            Log.e("error: ", t?.message + "\n")
                            t?.printStackTrace()
                        }
                    }
                }
            )
    }

    fun refresh() {
        clearCompositeDesposable()
        getFriends()
    }

}