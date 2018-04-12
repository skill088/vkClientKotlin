package com.projects.vo1.customvk.ui.dialogs

import android.util.Log
import com.projects.vo1.customvk.data.dialogs.Dialog
import com.projects.vo1.customvk.data.dialogs.DialogContainer
import com.projects.vo1.customvk.data.friends.FriendInfo
import com.projects.vo1.customvk.domain.dialogs.GetDialogsUseCase
import com.projects.vo1.customvk.domain.dialogs.GetInterlocutorsProfiles
import com.projects.vo1.customvk.ui.views.presenter.BasePresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PresenterDialogs(
    private val getDialogs: GetDialogsUseCase,
    private val getInterlocutorsProfiles: GetInterlocutorsProfiles,
    private val view: DialogsView
) : BasePresenter() {

    fun getDialogs() {
        compositeDisposable.add(
            doDialogsQuery(0)
                .subscribe(
                    {
                        view.showMessages(it)
                    },
                    this::onError
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
                    this::onError
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
                    this::onError
                )
        )
    }

    private fun doDialogsQuery(offset: Int): Single<List<Dialog>> {
        return getDialogs.execute(offset)
            .flatMap { list ->
                getInterlocutorsProfiles.execute(
                    list.map { it.dialog.userId })
                    .doOnSuccess { t ->
                        bindUsersToDialogs(list, t)
                    }
                    .map { list.map { it.dialog } }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun onError(t: Throwable) {
        Log.e("error: ", t.message)
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