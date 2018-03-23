package com.projects.vo1.customvk.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class ApiResponseList<T>(
                    @SerializedName("count")
                    val count: Int,
                    @SerializedName("items")
                    val items: List<T>
            )