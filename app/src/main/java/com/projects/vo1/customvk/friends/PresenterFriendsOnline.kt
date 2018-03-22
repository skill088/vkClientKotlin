package com.projects.vo1.customvk.friends

import android.util.Log
import com.projects.vo1.customvk.data.friends.FriendsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Admin on 21.03.2018.
 */
class PresenterFriendsOnline(private val friendsRepository: FriendsRepository,
                             private val view: FriendsOnlineView) {


    fun getOnlineFriends() {
        friendsRepository.getOnline()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ t: Friends? ->  Log.i("friends online: ", t.toString())})
    }
}
