package com.projects.vo1.customvk.friends

import android.util.Log
import com.projects.vo1.customvk.data.friends.FriendsRepository
import com.projects.vo1.customvk.model.Friends
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import io.reactivex.internal.disposables.DisposableHelper.dispose
import io.reactivex.disposables.Disposable
import rx.Observer


/**
 * Created by Admin on 21.03.2018.
 */
class PresenterFriendsOnline(private val friendsRepository: FriendsRepository,
                             private val view: FriendsOnlineView) {


    fun getOnlineFriends() {
        friendsRepository.getOnline()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .blockingFirst()
                .forEach { friend: Friends -> Log.i("response: ", friend.toString()) }
    }
}
