package com.projects.vo1.customvk.dialogs

import com.google.gson.annotations.SerializedName

class DialogContainer (
    @SerializedName("message")
    val dialog: Dialog,

    @SerializedName("in_read")
    val inRead: Int,

    @SerializedName("out_read")
    val outRead: Int
)