package com.example.myapplication.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Option(
    @SerializedName("icon")
    var icon: String? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null
)