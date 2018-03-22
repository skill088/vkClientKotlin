package com.projects.vo1.customvk.data.friends

import android.content.Context
import android.preference.PreferenceManager
import com.projects.vo1.customvk.data.api.friends.ApiFriends
import com.projects.vo1.customvk.friends.Friends
import io.reactivex.Observable

/**
 * Created by Admin on 21.03.2018.
 */
class FriendsRepository(private val apiFriends: ApiFriends, val context: Context) :
        IFriendsRepository {

    private val token: String? = PreferenceManager.getDefaultSharedPreferences(context)
            .getString("TOKEN", null)

    override fun getAll(): Observable<Friends> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOnline(): Observable<Friends> {
        return apiFriends.getOnline(token?: null.toString())
    }
}