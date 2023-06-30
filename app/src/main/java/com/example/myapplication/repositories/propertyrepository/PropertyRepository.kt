package com.example.myapplication.repositories.propertyrepository

import android.annotation.SuppressLint
import android.util.Log
import com.example.myapplication.domain.entities.Property
import com.example.myapplication.repositories.daos.PropertyFilterModelDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PropertyRepository(private val propertyDao: PropertyFilterModelDao) :
    IPropertyRepository {

    @SuppressLint("CheckResult")
    override fun insert(property: Property) {
        propertyDao.insert(property)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d("RxJava", "Insert Success") },
                { Log.d("RxJava", "Insert Error") }
            )
    }

    @SuppressLint("CheckResult")
    override fun update(property: Property) {
        propertyDao.update(property)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d("RxJava", "Update Success") },
                { Log.d("RxJava", "Update Error") }
            )
    }

    @SuppressLint("CheckResult")
    override fun delete(property: Property) {
        propertyDao.delete(property)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d("RxJava", "Delete Success") },
                { Log.d("RxJava", "Delete Error") }
            )
    }

    @SuppressLint("CheckResult")
    override fun deleteAllProperties() {
        Completable.fromAction{ propertyDao.deleteAllProperties() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d("RxJava", "Delete all Success") },
                { Log.d("RxJava", "Delete all Error") }
            )
    }

    override fun getAllProperties(): Observable<Property> = propertyDao.getAllProperty()
}