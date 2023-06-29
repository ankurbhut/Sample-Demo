package com.example.myapplication.adapter.listener

import android.view.View

interface IItemClickListener<T> {
    companion object {
        const val CLICK = 1
        const val ADD = 2
        const val DELETE = 3
        const val UPDATE = 4
        const val EMPTY = 5
        const val LOAD_MORE = 6
        const val LONG_CLICK = 7
    }

    fun onItemClick(view: View?, position: Int, actionType: Int?, data: T?)
}