package com.projects.vo1.customvk.ui.profile

import com.projects.vo1.customvk.data.profile.ProfileInfo

interface ProfileView {

    fun setProfile(profile: ProfileInfo)
    fun showError()
}