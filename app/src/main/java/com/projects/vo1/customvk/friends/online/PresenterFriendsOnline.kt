package com.projects.vo1.customvk.friends.online

import android.util.Log
import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.data.network.Error
import com.projects.vo1.customvk.friends.FriendInfo
import com.projects.vo1.customvk.friends.FriendsView
import com.projects.vo1.customvk.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class PresenterFriendsOnline(
    private val friendsRepository: FriendsRepositoryImpl,
    private val view: FriendsView
) : BasePresenter() {

    private var friendsList = mutableListOf<FriendInfo>()

    fun getOnlineFriends() {
        compositeDisposable.add(
            friendsRepository.getOnlineFriends(0)
                .flatMap { ids ->
                    friendsRepository.getUserInfos(ids.response!! as List<Long>)
                }
                .map { friend -> friendsList.addAll(friend.response!!) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.showFriends(friendsList)
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
        friendsList.clear()
        friendsRepository.getOnlineFriends(offset)
            .flatMap { ids ->
                friendsRepository.getUserInfos(ids.response!! as List<Long>)
            }
            .map { friend -> friendsList.addAll(friend.response!!) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.showMore(friendsList)
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
        friendsList.clear()
        getOnlineFriends()
    }
}
