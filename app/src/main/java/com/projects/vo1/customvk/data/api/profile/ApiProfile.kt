package com.projects.vo1.customvk.data.api.profile

import com.projects.vo1.customvk.data.network.response.ApiResponseObject
import com.projects.vo1.customvk.profile.ProfileInfo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Admin on 27.03.2018.
 */
interface ApiProfile {

    @GET("users.get?v=5.73&fields=photo_400_orig,city,bdate,has_mobile")
    fun getUserInfo(@Query("access_token") token: String,
                    @Query("user_id") id: String?): Single<ApiResponseProfile>
}

typealias ApiResponseProfile = ApiResponseObject<List<ProfileInfo>>