package com.projects.vo1.customvk.dialogs

import android.util.Log
import com.projects.vo1.customvk.data.dialogs.DialogsRepository
import com.projects.vo1.customvk.data.friends.FriendsRepositoryImpl
import com.projects.vo1.customvk.friends.FriendInfo
import com.projects.vo1.customvk.presenter.BasePresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PresenterDialogs(
    private val dialogsRepository: DialogsRepository,
    private val friendsRepository: FriendsRepositoryImpl,
    private val view: DialogsView
) : BasePresenter() {

    fun getDialogs() {
        compositeDisposable.add(
            doDialogsQuery(0)
                .subscribe(
                    {
                        view.showMessages(it)
                    },
                    {
                        Log.e("error: ", it.message)
                    }
                )
        )
    }

    fun loadMore(offset: Int) {
        compositeDisposable.add(
            doDialogsQuery(offset)
                .subscribe(
                    {
                        view.showMore(it)
                    },
                    {
                        Log.e("error: ", it.message)
                    }
                )
        )
    }

    fun reload() {
        compositeDisposable.add(
            doDialogsQuery(0)
                .subscribe(
                    {
                        view.reload(it)
                    },
                    {
                        Log.e("error: ", it.message)
                    }
                )
        )
    }

    private fun doDialogsQuery(offset: Int): Single<List<Dialog>> {
        return dialogsRepository.getDialogs(offset)
            .flatMap { list ->
                friendsRepository.getUserInfos(
                    list.response?.items!!.map { it.dialog.userId })
                    .doOnSuccess { t ->
                        bindUsersToDialogs(list.response.items, t.response!!)
                    }
                    .map { list.response.items.map { it.dialog } }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun bindUsersToDialogs(list: List<DialogContainer>, profilesList: List<FriendInfo>) {
        list.forEach { t ->
            t.dialog.userName = profilesList.find { it.id == t.dialog.userId }.toString()
            t.dialog.userPhoto =
                    if (t.dialog.out != 1) profilesList.find { it.id == t.dialog.userId }?.photo
                    else profilesList.find { it.id == 37374876L }?.photo // TODO: ЗАМЕНИТЬ НА ID ТЕКУЩЕГО ЮЗЕРА, СОХРАНЯТЬ ЕГО В ШАРЕДПРЕФ ПРИ АВТОРИЗАЦИИ
            if (t.dialog.photo == null)
                t.dialog.photo = profilesList.find { it.id == t.dialog.userId }?.photo
        }
    }
}