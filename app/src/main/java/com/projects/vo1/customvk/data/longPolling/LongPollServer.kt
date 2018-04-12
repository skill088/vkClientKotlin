package com.projects.vo1.customvk.data.longPolling

import com.google.gson.annotations.SerializedName

class LongPollServer (
    @SerializedName("key")
    val key: String,
    @SerializedName("server")
    val server: String,
    @SerializedName("pts")
    var pts: Int
) : LongPollBaseResponse()