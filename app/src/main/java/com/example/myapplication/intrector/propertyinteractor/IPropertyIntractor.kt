package com.example.myapplication.intrector.propertyinteractor

import com.example.myapplication.domain.entities.Property
import io.reactivex.Observable

interface IPropertyIntractor {

    fun insert(property: Property)

    fun update(property: Property)

    fun delete(property: Property)

    fun deleteAllProperties()

    fun getAllProperties(): Observable<Property>
}