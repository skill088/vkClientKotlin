package com.projects.vo1.customvk.friends

import android.util.Log
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Admin on 21.03.2018.
 */
class FriendsIds {

    @SerializedName("response")
    @Expose
    var idList: IntArray? = null

    override fun toString(): String {
        var str = ""
        idList?.forEach { i ->  str += i.toString() + ","}
        return str
    }
}