package com.projects.vo1.customvk.data.api.friends

import com.projects.vo1.customvk.model.Friends
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Admin on 21.03.2018.
 */
interface ApiFriends {

    @GET("friends.getOnline?order=hints?v=5.73")
    fun getOnline(@Query("access_token") token: String): Observable<List<Friends>>
}