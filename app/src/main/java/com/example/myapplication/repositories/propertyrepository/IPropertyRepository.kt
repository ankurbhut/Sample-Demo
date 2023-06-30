package com.example.myapplication.repositories.propertyrepository

import com.example.myapplication.domain.entities.Property
import io.reactivex.Observable

interface IPropertyRepository {

    fun insert(property: Property)

    fun update(property: Property)

    fun delete(property: Property)

    fun deleteAllProperties()

    fun getAllProperties(): Observable<Property>
}

