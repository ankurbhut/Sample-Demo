package com.example.myapplication.network

import com.example.myapplication.model.PropertyFilterModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by anujgupta on 26/12/17.
 */
interface NetworkInterface {

    @GET("ad-assignment/db")
    fun getPropertyFilter(): Observable<PropertyFilterModel>
}