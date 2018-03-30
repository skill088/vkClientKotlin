package com.projects.vo1.customvk.dialogs.details

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Message {
    @SerializedName("id")
    val id: Int? = null
    @SerializedName("body")
    val body: String? = null
    @SerializedName("user_id")
    val userId: Int? = null
    @SerializedName("from_id")
    val fromId: Int? = null
    @SerializedName("date")
    val date: Int? = null
    @SerializedName("read_state")
    val readState: Int? = null
    @SerializedName("out")
    val out: Int? = null
    @SerializedName("random_id")
    val randomId: Int? = null
}