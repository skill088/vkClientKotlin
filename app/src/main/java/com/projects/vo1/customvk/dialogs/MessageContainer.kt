package com.projects.vo1.customvk.dialogs

import com.google.gson.annotations.SerializedName

/**
 * Created by Admin on 29.03.2018.
 */
class MessageContainer {

    @SerializedName("message")
    val message: Message? = null

    @SerializedName("in_read")
    val inRead: Int? = null

    @SerializedName("out_read")
    val outRead: Int? = null

}