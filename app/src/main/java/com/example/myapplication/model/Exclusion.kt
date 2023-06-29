package com.example.myapplication.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Exclusion(
    @SerializedName("facility_id")
    var facilityId: String? = null,
    @SerializedName("options_id")
    var optionsId: String? = null
)