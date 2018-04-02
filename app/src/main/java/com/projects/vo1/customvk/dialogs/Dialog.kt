package com.projects.vo1.customvk.dialogs

import com.google.gson.annotations.SerializedName

class Dialog (

    @SerializedName("id")
    val id: Int,
    @SerializedName("date")
    val date: Long,
    @SerializedName("out")
    val out: Int,
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("read_state")
    val readState: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("chat_id")
    val chatId: Long? = null,
    @SerializedName("photo_200")
    var photo: String? = null,
    var userName: String? = null,
    var userPhoto: String? = null
)