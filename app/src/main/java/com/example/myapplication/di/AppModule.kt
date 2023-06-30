package com.example.myapplication.di

import androidx.room.Room
import com.example.myapplication.intrector.propertyinteractor.IPropertyIntractor
import com.example.myapplication.intrector.propertyinteractor.PropertyIntractor
import com.example.myapplication.presenter.propertypresenter.PropertyPresenter
import com.example.myapplication.repositories.databases.AppDatabase
import com.example.myapplication.repositories.propertyrepository.IPropertyRepository
import com.example.myapplication.repositories.propertyrepository.PropertyRepository
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {

    single { Room.databaseBuilder(get(), AppDatabase::class.java, "property_filter_database").build() }

    single { get<AppDatabase>().propertyDao() }

    single<IPropertyRepository> {
        PropertyRepository(
            get()
        )
    }

    single<IPropertyIntractor> {
        PropertyIntractor(
            get()
        )
    }

    viewModel { PropertyPresenter(get()) }
}
