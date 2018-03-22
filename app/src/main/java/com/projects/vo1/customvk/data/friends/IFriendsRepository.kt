package com.projects.vo1.customvk.data.friends

import com.projects.vo1.customvk.friends.Friends
import io.reactivex.Observable

interface IFriendsRepository {

    fun getAll(): Observable<Friends>
    fun getOnline(): Observable<Friends>
}