package com.projects.vo1.customvk.friends

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class FriendInfo {

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("first_name")
    @Expose
    val firstName: String? = null

    @SerializedName("last_name")
    @Expose
    val lastName: String? = null

    @SerializedName("photo_100")
    @Expose
    val photo: String? = null

    override fun toString(): String {
        return "id: $id name: $firstName last name: $lastName avatar: $photo"
    }
}