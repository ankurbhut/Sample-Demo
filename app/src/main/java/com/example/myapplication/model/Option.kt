package com.example.myapplication.model


import android.graphics.drawable.Drawable
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.example.myapplication.R

@Keep
data class Option(
    @SerializedName("icon")
    var icon: String? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null
) {
    var isDisable: Boolean = false

    fun getDrawableRes(): Int {
        return when (icon) {
            "apartment" -> R.drawable.ic_apartment
            "condo" -> R.drawable.ic_condo
            "boat" -> R.drawable.ic_boat
            "land" -> R.drawable.ic_land
            "rooms" -> R.drawable.ic_rooms
            "no-room" -> R.drawable.ic_no_room
            "swimming" -> R.drawable.ic_swimming
            "garden" -> R.drawable.ic_garden
            "garage" -> R.drawable.ic_garage
            else -> R.drawable.ic_apartment
        }
    }
}