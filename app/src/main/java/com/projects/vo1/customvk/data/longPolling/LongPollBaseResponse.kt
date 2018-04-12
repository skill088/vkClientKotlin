package com.projects.vo1.customvk.data.longPolling

import com.google.gson.annotations.SerializedName

open class LongPollBaseResponse {

    @SerializedName("failed")
    val failed: Int? = null
    @SerializedName("ts")
    val ts: Long?= null
}