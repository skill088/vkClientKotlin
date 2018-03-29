package com.projects.vo1.customvk.data.friends

import android.content.Context
import android.preference.PreferenceManager
import com.projects.vo1.customvk.data.api.friends.ApiFriends
import com.projects.vo1.customvk.data.api.friends.ApiResponseFriendsAll
import com.projects.vo1.customvk.friends.FriendInfo
import com.projects.vo1.customvk.data.network.response.ApiResponseObject
import com.projects.vo1.customvk.data.utils.Transformer.errorTransformer
import io.reactivex.Single


class FriendsRepositoryImpl(private val apiFriends: ApiFriends, val context: Context) :
    FriendsRepository {

    private val token: String? = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("TOKEN", null)

    override fun getAll(offset: Int): Single<ApiResponseFriendsAll> {
        return apiFriends.getFriends(token ?: null.toString(), offset)
            .compose(errorTransformer())
    }

    override fun getOnline(offset: Int): Single<ApiResponseObject<LongArray>> {
        return apiFriends.getOnline(token ?: null.toString(), offset)
            .compose(errorTransformer())
    }

    override fun getOnlineInfo(ids: String): Single<ApiResponseObject<List<FriendInfo>>> {
        return apiFriends.getInfoOnline(token ?: null.toString(), ids)
            .compose(errorTransformer())
    }
}