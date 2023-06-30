package com.example.myapplication.adapter.listener

import android.view.View

interface IItemClickListener<T> {
    companion object {
        const val CLICK = 1
    }

    fun onItemClick(view: View?, position: Int, actionType: Int?, data: T?)
}