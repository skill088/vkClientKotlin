package com.projects.vo1.customvk.dialogs

import com.google.gson.annotations.SerializedName

class Message {

    @SerializedName("id")
    val id: Int? = null
    @SerializedName("date")
    val date: Long? = null
    @SerializedName("out")
    val out: Int? = null
    @SerializedName("user_id")
    val userId: Int? = null
    @SerializedName("read_state")
    val readState: Int? = null
    @SerializedName("title")
    val title: String? = null
    @SerializedName("body")
    val body: String? = null
    @SerializedName("chat_id")
    val chatId: Int? = null
    @SerializedName("photo_200")
    val photo: String? = null
    var userPhoto: String? = null
}