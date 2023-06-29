package com.example.myapplication.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

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