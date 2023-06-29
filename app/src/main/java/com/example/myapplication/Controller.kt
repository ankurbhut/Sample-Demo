package com.example.myapplication

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.myapplication.network.NetworkClient


class Controller : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        NetworkClient.initialize()
        observeApplicationState()
    }

    companion object {
        lateinit var instance: Controller
    }

    private fun observeApplicationState() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleEventObserver)
    }

    private val lifecycleEventObserver = LifecycleEventObserver { owner, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {

            }

            Lifecycle.Event.ON_PAUSE -> {

            }

            Lifecycle.Event.ON_STOP -> {

            }

            Lifecycle.Event.ON_CREATE, Lifecycle.Event.ON_START -> {

            }

            else -> {
            }
        }
    }

}