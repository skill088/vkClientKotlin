package com.projects.vo1.customvk.domain.profile

import com.projects.vo1.customvk.data.profile.ProfileInfo
import io.reactivex.Single

interface ProfileRepository {

    fun getUserInfo(id: String?): Single<ProfileInfo>
}