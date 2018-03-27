package com.projects.vo1.customvk.data.profile

import com.projects.vo1.customvk.proffile.ProfileInfo
import io.reactivex.Single

interface ProfileRepository {

    fun getUserInfo(id: String?): Single<ProfileInfo>
}