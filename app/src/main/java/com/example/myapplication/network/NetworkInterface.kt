package com.example.myapplication.network

import com.example.myapplication.domain.entities.Property
import io.reactivex.Observable
import retrofit2.http.GET

interface NetworkInterface {

    @GET("ad-assignment/db")
    fun getPropertyFilter(): Observable<Property>
}