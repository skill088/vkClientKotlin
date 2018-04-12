package com.projects.vo1.customvk.domain.profile

import com.projects.vo1.customvk.data.profile.ProfileInfo
import com.projects.vo1.customvk.domain.UseCase
import io.reactivex.Single

class GetUserProfileUseCase(private val profileRepository: ProfileRepository) :
    UseCase<String?, ProfileInfo> {

    override fun execute(param: String?): Single<ProfileInfo> {
        return profileRepository.getUserInfo(param)
    }
}