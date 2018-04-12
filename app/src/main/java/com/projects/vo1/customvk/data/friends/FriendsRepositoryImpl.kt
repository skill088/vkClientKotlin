package com.projects.vo1.customvk.data.friends

import android.content.Context
import android.preference.PreferenceManager
import com.projects.vo1.customvk.data.data.api.friends.ApiFriends
import com.projects.vo1.customvk.data.data.api.friends.ApiResponseFriendsAll
import com.projects.vo1.customvk.data.data.utils.Transformer.errorTransformer
import com.projects.vo1.customvk.data.network.response.ApiResponseObject
import com.projects.vo1.customvk.domain.friends.FriendsRepository
import io.reactivex.Single


class FriendsRepositoryImpl(private val apiFriends: ApiFriends, val context: Context) :
    FriendsRepository {

    private val token: String? = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("TOKEN", null)

    override fun getAll(offset: Int): Single<List<FriendInfo>> {
        return apiFriends.getFriends(token ?: null.toString(), offset)
            .compose(errorTransformer<ApiResponseFriendsAll>())
            .map { it.response!!.items }
    }

    override fun getOnlineFriends(offset: Int): Single<ApiResponseObject<LongArray>> {
        return apiFriends.getOnline(token ?: null.toString(), offset)
            .compose(errorTransformer())
    }

    override fun getUserInfos(ids: List<Long>): Single<List<FriendInfo>> {
        return apiFriends.getInfoOnline(token ?: null.toString(), ids.joinToString(","))
            .compose(errorTransformer<ApiResponseObject<List<FriendInfo>>>())
            .map { it.response }
    }
}