package com.projects.vo1.customvk.dialogs.details

import com.google.gson.annotations.SerializedName

class Message (
    @SerializedName("id")
    val id: Int,
    @SerializedName("body")
    var body: String,
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("from_id")
    val fromId: Long,
    @SerializedName("date")
    var date: Long,
    @SerializedName("read_state")
    val readState: Int,
    @SerializedName("out")
    val out: Int,
    @SerializedName("random_id")
    val randomId: Int,
    var photo: String?
)