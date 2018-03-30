package com.projects.vo1.customvk.friends

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class FriendInfo {

    @SerializedName("id")
    val id: Long? = null

    @SerializedName("first_name")
    val firstName: String? = null

    @SerializedName("last_name")
    val lastName: String? = null

    @SerializedName("photo_100")
    val photo: String? = null

    override fun toString(): String {
        return "$firstName $lastName"
    }
}