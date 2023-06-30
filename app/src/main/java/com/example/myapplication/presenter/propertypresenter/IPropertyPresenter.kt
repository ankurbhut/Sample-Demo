package com.example.myapplication.presenter.propertypresenter

import androidx.lifecycle.LiveData
import com.example.myapplication.domain.entities.Property

interface IPropertyPresenter {

    fun insert(property: Property)

    fun update(property: Property)

    fun delete(property: Property)

    fun deleteAllProperties()

    fun getAllProperties(): LiveData<Property>

    fun getAllApiProperties(): LiveData<Property>

    fun getAllErrors(): LiveData<String>

}