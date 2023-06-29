package com.example.myapplication.ui.main

import android.util.Log
import com.example.myapplication.domain.entities.Property
import com.example.myapplication.network.NetworkClient
import com.example.myapplication.network.NetworkInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


class MainPresenter(var mvi: MainViewInterface) : MainPresenterInterface {

//    private val TAG = "MainPresenter"
//
//
//    private fun getObservable(): Observable<Property> {
//        mvi.showProgressBar()
//        return NetworkClient.retrofit.create(NetworkInterface::class.java)
//            .getPropertyFilter()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//    }
//
//    private fun getObserver(): DisposableObserver<Property> {
//        return object : DisposableObserver<Property>() {
//            override fun onNext(propertyFilterResponse: Property) {
//                mvi.displayPropertyFilters(propertyFilterResponse)
//            }
//
//            override fun onError(e: Throwable) {
//                Log.d(TAG, "Error$e")
//                e.printStackTrace()
//                mvi.displayError("Error fetching Movie Data")
//            }
//
//            override fun onComplete() {
//                Log.d(TAG, "Completed")
//                mvi.hideProgressBar()
//            }
//        }
//    }

    override fun getPropertyFilters() {
//        getObservable().subscribeWith(getObserver())
    }
}