package com.projects.vo1.customvk.data.network.response

import com.google.gson.annotations.SerializedName

open class ApiResponseList<T>(
                    @SerializedName("count")
                    val count: Int? = null,
                    @SerializedName("items")
                    val items: List<T>
            )