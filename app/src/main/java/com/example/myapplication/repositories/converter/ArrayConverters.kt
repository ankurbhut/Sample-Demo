package com.example.myapplication.repositories.converter

import androidx.room.TypeConverter
import com.example.myapplication.model.Exclusion
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object ArrayConverters {

    @TypeConverter
    fun fromString(value: String?): ArrayList<ArrayList<Exclusion>> {
        val listType: Type = object : TypeToken<ArrayList<ArrayList<Exclusion>>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<ArrayList<Exclusion>>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}