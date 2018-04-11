package com.projects.vo1.customvk.friends

import com.google.gson.annotations.SerializedName

class FriendInfo (
    @SerializedName("id")
    val id: Long,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("photo_100")
    val photo: String
) {
    override fun toString(): String {
        return "$firstName $lastName"
    }
}