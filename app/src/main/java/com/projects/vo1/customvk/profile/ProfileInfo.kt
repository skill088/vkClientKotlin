package com.projects.vo1.customvk.profile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProfileInfo {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("first_name")
    @Expose
    var firstName: String? = null
    @SerializedName("last_name")
    @Expose
    var lastName: String? = null
    @SerializedName("bdate")
    @Expose
    var bdate: String? = null
    @SerializedName("city")
    @Expose
    var city: City? = null
    @SerializedName("photo_400_orig")
    @Expose
    var photoMax: String? = null
    @SerializedName("has_mobile")
    @Expose
    var hasMobile: Int? = null

    data class City(
        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("title")
        @Expose
        var title: String
    )
}