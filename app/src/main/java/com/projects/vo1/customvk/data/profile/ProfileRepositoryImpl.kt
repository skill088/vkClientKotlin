package com.projects.vo1.customvk.data.profile

import android.content.Context
import android.preference.PreferenceManager
import com.projects.vo1.customvk.data.api.profile.ApiProfile
import com.projects.vo1.customvk.data.api.profile.ApiResponseProfile
import com.projects.vo1.customvk.proffile.ProfileInfo
import io.reactivex.Observable
import io.reactivex.Single

class ProfileRepositoryImpl(private val apiProfile: ApiProfile, val context: Context) :
    ProfileRepository {

    private val token: String? = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("TOKEN", null)

    override fun getUserInfo(id: String?): Single<ProfileInfo> {
        return apiProfile.getUserInfo(token?: null.toString(), id)
            .map { it.response?.get(0) }
    }
}