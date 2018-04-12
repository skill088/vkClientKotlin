package com.projects.vo1.customvk.data.longPolling

import com.google.gson.annotations.SerializedName
import com.projects.vo1.customvk.data.longPolling.LongPollBaseResponse

class LongPollResponse(
    @SerializedName("updates")
    val updates: List<List<Any>>
)  : LongPollBaseResponse()

data class Updates (
    val status: Int,
    val messageId: Long,
    val ballast: Long,
    val interlocutorId: Long,
    val time: Long,
    val body: String) {
    val x = object {
        var from: Long = 0
    }
}