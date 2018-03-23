package com.projects.vo1.customvk.friends

import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.presenter.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PresenterAllFriends(private val friendsRepository: FriendsRepositoryImpl,
                          private val view: FriendsView) : BasePresenter() {

    var friendsList = mutableListOf<FriendInfo>()

    fun getFriends() {
        compositeDisposable.add(
            friendsRepository.getAll(0)
                .flatMap { ids ->  friendsRepository.getAllInfo(ids.toString())}
                .flatMap { infos -> Observable.fromIterable(infos.response?.items) }
                .map { friend -> friendsList.add(friend) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnComplete(
                    {
                        view.showFriends(friendsList)
                        view.hideSwipeRefresh()
                    })
                .subscribe()
        )
    }

    fun refresh() {
        friendsList.clear()
        getFriends()
    }

}