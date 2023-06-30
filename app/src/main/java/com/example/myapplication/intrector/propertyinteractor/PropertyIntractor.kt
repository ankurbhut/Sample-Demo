package com.example.myapplication.intrector.propertyinteractor

import com.example.myapplication.domain.entities.Property
import com.example.myapplication.repositories.propertyrepository.IPropertyRepository
import io.reactivex.Observable

class PropertyIntractor(private val iPropertyRepository: IPropertyRepository) :
    IPropertyIntractor {

    override fun insert(property: Property) {
        iPropertyRepository.insert(property)
    }

    override fun update(property: Property) {
        iPropertyRepository.update(property)
    }

    override fun delete(property: Property) {
        iPropertyRepository.delete(property)
    }

    override fun deleteAllProperties() {
        iPropertyRepository.deleteAllProperties()
    }

    override fun getAllProperties(): Observable<Property> {
        return iPropertyRepository.getAllProperties()
    }
}