package com.example.myapplication.presenter.propertypresenter

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.entities.Property
import com.example.myapplication.intrector.propertyinteractor.IPropertyIntractor
import com.example.myapplication.network.NetworkClient
import com.example.myapplication.network.NetworkInterface
import com.example.myapplication.utility.LocalDataHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class PropertyPresenter(private val iPropertyIntractor: IPropertyIntractor) : IPropertyPresenter,
    ViewModel() {

    private val TAG = "MainPresenter"
    private val allProperties: MutableLiveData<Property> = MutableLiveData()
    private val allApiProperties: MutableLiveData<Property> = MutableLiveData()
    private val allErrors: MutableLiveData<String> = MutableLiveData()

    override fun insert(property: Property) {
        iPropertyIntractor.insert(property)
    }

    override fun update(property: Property) {
        iPropertyIntractor.update(property)
    }

    override fun delete(property: Property) {
        iPropertyIntractor.delete(property)
    }

    override fun deleteAllProperties() {
        iPropertyIntractor.deleteAllProperties()
    }

    override fun getAllProperties(): LiveData<Property> {
        return allProperties
    }

    override fun getAllApiProperties(): LiveData<Property> {
        return allApiProperties
    }

    override fun getAllErrors(): LiveData<String> {
        return allErrors
    }

    @SuppressLint("CheckResult")
    fun loadProperties() {
        iPropertyIntractor.getAllProperties()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { property -> allProperties.postValue(property) },
                { error -> Log.d("RxJava", "Error getting info from interactor into presenter") }
            )
    }


    private fun getObservable(): Observable<Property> {
        return NetworkClient.retrofit.create(NetworkInterface::class.java)
            .getPropertyFilter()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getObserver(): DisposableObserver<Property> {
        return object : DisposableObserver<Property>() {
            override fun onNext(propertyFilterResponse: Property) {
                allApiProperties.postValue(propertyFilterResponse)
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                allErrors.postValue("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }
    }

    fun getPropertyFilters() {
        val date: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        if (LocalDataHelper.getCurrentDate().isEmpty() || date != LocalDataHelper.getCurrentDate()) {
            LocalDataHelper.setCurrentDate(date)
            deleteAllProperties()
            getObservable().subscribeWith(getObserver())
        } else {
            loadProperties()
        }
    }
}