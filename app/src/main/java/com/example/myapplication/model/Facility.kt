package com.example.myapplication.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Facility(
    @SerializedName("facility_id")
    var facilityId: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("options")
    var options: ArrayList<Option> = ArrayList()
) {
    var isSelected: Boolean = false
}