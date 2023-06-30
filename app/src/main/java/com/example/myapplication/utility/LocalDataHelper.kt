package com.example.myapplication.utility


import com.example.myapplication.utility.PreferenceManager.Companion.getInstance
import com.example.myapplication.utility.SharedPrefConstant.CURRENT_DATE

object LocalDataHelper {

    fun setCurrentDate(date: String) {
        getInstance().putString(CURRENT_DATE, date)
    }

    fun getCurrentDate(): String {
        return getInstance().getString(CURRENT_DATE, "") ?: ""
    }
}