package com.example.myapplication.repositories.converter

import androidx.room.TypeConverter
import com.example.myapplication.model.Facility
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object Converters {

    @TypeConverter
    fun fromString(value: String?): ArrayList<Facility> {
        val listType: Type = object : TypeToken<ArrayList<Facility>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Facility>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}