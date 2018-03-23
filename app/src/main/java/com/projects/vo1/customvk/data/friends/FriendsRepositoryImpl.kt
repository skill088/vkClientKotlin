package com.projects.vo1.customvk.data.friends

import android.content.Context
import android.preference.PreferenceManager
import com.projects.vo1.customvk.data.api.friends.ApiFriends
import com.projects.vo1.customvk.friends.FriendInfo
import com.projects.vo1.customvk.friends.FriendsIds
import com.projects.vo1.customvk.model.ApiResponseList
import com.projects.vo1.customvk.model.ApiResponseObject
import io.reactivex.Observable

/**
 * Created by Admin on 21.03.2018.
 */
class FriendsRepositoryImpl(private val apiFriends: ApiFriends, val context: Context) :
        IFriendsRepository {

    private val token: String? = PreferenceManager.getDefaultSharedPreferences(context)
            .getString("TOKEN", null)

    override fun getAll(offset: Int): Observable<FriendsIds> {
        return apiFriends.getFriends(token?: null.toString(), offset)
    }

    override fun getOnline(): Observable<FriendsIds> {
        return apiFriends.getOnline(token?: null.toString())
    }

    override fun getOnlineInfo(ids: String): Observable<ApiResponseObject<List<FriendInfo>>> {
        return apiFriends.getInfoOnline(token?: null.toString(), ids)
    }

    override fun getAllInfo(ids: String): Observable<ApiResponseObject<ApiResponseList<FriendInfo>>> {
        return apiFriends.getInfoAll(token?: null.toString(), ids)
    }
}