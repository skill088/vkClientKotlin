package com.projects.vo1.customvk.data.network.response

import com.google.gson.annotations.SerializedName

open class ApiResponseObject<T> : ApiError(){
    @SerializedName("response")
    val response: T? = null
}