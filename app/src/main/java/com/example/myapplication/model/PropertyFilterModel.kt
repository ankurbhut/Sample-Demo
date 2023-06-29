package com.example.myapplication.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PropertyFilterModel(
    @SerializedName("exclusions")
    var exclusions: ArrayList<ArrayList<Exclusion>> = ArrayList(),
    @SerializedName("facilities")
    var facilities: ArrayList<Facility> = ArrayList()
)