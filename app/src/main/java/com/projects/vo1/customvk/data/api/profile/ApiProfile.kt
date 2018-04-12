package com.projects.vo1.customvk.data.data.api.profile

import com.projects.vo1.customvk.data.network.response.ApiResponseObject
import com.projects.vo1.customvk.data.profile.ProfileInfo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiProfile {

    @GET("users.get?v=5.73&fields=photo_400_orig,city,bdate,has_mobile")
    fun getUserInfo(@Query("access_token") token: String,
                    @Query("user_id") id: String?): Single<ApiResponseProfile>
}

typealias ApiResponseProfile = ApiResponseObject<List<ProfileInfo>>