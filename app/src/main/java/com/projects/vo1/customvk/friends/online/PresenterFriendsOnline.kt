package com.projects.vo1.customvk.friends.online

import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.friends.FriendInfo
import com.projects.vo1.customvk.friends.FriendsView
import com.projects.vo1.customvk.presenter.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PresenterFriendsOnline(private val friendsRepository: FriendsRepositoryImpl,
                             private val view: FriendsView
) : BasePresenter() {

    var friendsList = mutableListOf<FriendInfo>()

    fun getOnlineFriends() {
        compositeDisposable.add(
                friendsRepository.getOnline(0)
                    .flatMap { ids ->  friendsRepository.getOnlineInfo(ids.toString())}
                    .flatMap { infos -> Observable.fromIterable(infos.response) }
                    .map { friend -> friendsList.add(friend) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete(
                        {
                            view.showFriends(friendsList)
                            view.hideSwipeRefresh()
                        })
                    .subscribe {  }
        )
    }

    fun loadMore(offset: Int) {
        friendsList.clear()
        friendsRepository.getOnline(offset)
                .flatMap { ids ->  friendsRepository.getOnlineInfo(ids.toString())}
                .flatMap { infos -> Observable.fromIterable(infos.response) }
                .map { friend -> friendsList.add(friend) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    view.showMore(friendsList)
                }
                .subscribe()
    }

    fun refresh() {
        friendsList.clear()
        getOnlineFriends()
    }
}
