package com.projects.vo1.customvk.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class ApiResponseObject<T> {
    @SerializedName("response")
    val response: T? = null
}