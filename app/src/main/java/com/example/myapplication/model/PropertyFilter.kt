package com.example.myapplication.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.model.Exclusion
import com.example.myapplication.model.Facility

@Keep
data class PropertyFilter(
    @SerializedName("exclusions")
    var exclusions: ArrayList<ArrayList<Exclusion>> = ArrayList(),

    @SerializedName("facilities")
    var facilities: ArrayList<Facility> = ArrayList()
)