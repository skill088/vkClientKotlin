package com.projects.vo1.customvk.data.nework.model_api_response

import com.google.gson.annotations.SerializedName

open class ApiResponseObject<T> {
    @SerializedName("response")
    val response: T? = null
}