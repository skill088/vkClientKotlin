package com.projects.vo1.customvk.dialogs

import com.google.gson.annotations.SerializedName

class Dialog {

    @SerializedName("id")
    val id: Int? = null
    @SerializedName("date")
    val date: Long? = null
    @SerializedName("out")
    val out: Int? = null
    @SerializedName("user_id")
    val userId: Long? = null
    @SerializedName("read_state")
    val readState: Int? = null
    @SerializedName("title")
    val title: String? = null
    @SerializedName("body")
    val body: String? = null
    @SerializedName("chat_id")
    val chatId: Int? = null
    @SerializedName("photo_200")
    var photo: String? = null
    var userName: String? = null
    var userPhoto: String? = null
}