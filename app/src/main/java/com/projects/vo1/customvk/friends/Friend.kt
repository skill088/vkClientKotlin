package com.projects.vo1.customvk.friends

import android.util.Log
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Admin on 21.03.2018.
 */
class Friend {

    @SerializedName("response")
    @Expose
    var idList: IntArray? = null

    override fun toString(): String {
        var str: String = ""
        idList?.forEach { i ->  str += i.toString() + "\n"}
        return str
    }
}