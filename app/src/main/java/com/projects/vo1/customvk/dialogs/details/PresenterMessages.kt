package com.projects.vo1.customvk.dialogs.details

import android.util.Log
import com.projects.vo1.customvk.data.dialogs.details.DialogDetailRepositoryImpl
import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.friends.FriendInfo
import com.projects.vo1.customvk.presenter.BasePresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PresenterMessages(private val dialogsDetailRep: DialogDetailRepositoryImpl,
                        private val friendsRepository: FriendsRepositoryImpl,
                        private val view: MessagesView) : BasePresenter() {

    fun getHistory(uid: Long) {
        compositeDisposable.add(
            doHistoryQuery(0, uid)
                .subscribe(
                    {
                        view.showMessages(it)
                    },
                    {}
                )
        )
    }

    fun loadMore(offset: Int, uid: Long) {
        compositeDisposable.add(
            doHistoryQuery(offset, uid)
                .subscribe(
                    {
                        view.showMore(it)
                    }, {
                        Log.e("load more messages",it.message)
                    }
                )
        )
    }

    fun sendMessage(uid: Long?, cid: Long?, body: String) {
        compositeDisposable.add(
            dialogsDetailRep.sendMessage(uid, cid, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.setSent(it.response?:-1)
                    }, {
                        Log.e("load more messages",it.message)
                    }
                )
        )
    }

    private fun doHistoryQuery(offset: Int, uid: Long): Single<List<Message>> {
        return dialogsDetailRep.getHistory(offset, uid)
            .flatMap { list ->
                friendsRepository.getUserInfos(
                    list.map { it.userId })
                    .doOnSuccess { t ->
                        bindUsersToDialogs(list, t.response!!)
                    }
                    .map { list }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun bindUsersToDialogs(list: List<Message>, profilesList: List<FriendInfo>) {
        list.forEach { t ->
            t.photo = profilesList.find { it.id == t.userId }?.photo
        }
    }
}