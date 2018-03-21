package com.projects.vo1.customvk.data.friends

import com.projects.vo1.customvk.model.Friends
import io.reactivex.Observable

/**
 * Created by Admin on 21.03.2018.
 */
interface IFriendsRepository {

    fun getAll(): Observable<List<Friends>>
    fun getOnline(): Observable<List<Friends>>
}