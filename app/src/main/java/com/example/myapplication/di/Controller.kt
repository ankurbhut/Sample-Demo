package com.example.myapplication.di

import android.app.Application
import com.example.myapplication.network.NetworkClient
import org.koin.android.ext.android.startKoin

class Controller : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin(this, listOf(appModule))
        NetworkClient.initialize()
    }

    companion object {
        lateinit var instance: Controller
    }
}