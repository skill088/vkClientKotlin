package com.projects.vo1.customvk.friends.online

import android.util.Log
import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.data.network.Error
import com.projects.vo1.customvk.data.network.response.ApiResponseObject
import com.projects.vo1.customvk.friends.FriendInfo
import com.projects.vo1.customvk.friends.FriendsView
import com.projects.vo1.customvk.presenter.BasePresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class PresenterFriendsOnline(
    private val friendsRepository: FriendsRepositoryImpl,
    private val view: FriendsView
) : BasePresenter() {

    fun getOnlineFriends() {
        compositeDisposable.add(
            doFriendsQuery(0)
                .subscribe(
                    {
                        view.showFriends(it.response!!)
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
        compositeDisposable.add(
            doFriendsQuery(offset)
            .subscribe(
                {
                    view.showMore(it.response!!)
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

    private fun doFriendsQuery(offset: Int): Single<ApiResponseObject<List<FriendInfo>>> {
        return friendsRepository.getOnlineFriends(offset)
            .flatMap { ids ->
                friendsRepository.getUserInfos(ids.response!!.toList())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun refresh() {
        clearCompositeDesposable()
        getOnlineFriends()
    }
}
