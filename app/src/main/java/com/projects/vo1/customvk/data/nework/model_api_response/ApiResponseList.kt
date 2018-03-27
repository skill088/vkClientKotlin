package com.projects.vo1.customvk.data.nework.model_api_response

import com.google.gson.annotations.SerializedName

open class ApiResponseList<T>(
                    @SerializedName("count")
                    val count: Int,
                    @SerializedName("items")
                    val items: List<T>
            )