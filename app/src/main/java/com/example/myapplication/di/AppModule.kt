package com.example.myapplication.di

import androidx.room.Room
import com.example.myapplication.intrector.quoteinteractor.IQuoteInteractor
import com.example.myapplication.intrector.quoteinteractor.QuoteInteractor
import com.example.myapplication.presenter.quotepresenter.QuotePresenter
import com.example.myapplication.repositories.databases.AppDatabase
import com.example.myapplication.repositories.quoterepository.IQuoteRepository
import com.example.myapplication.repositories.quoterepository.QuoteRepository
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {

    single { Room.databaseBuilder(get(), AppDatabase::class.java, "property_filter_database").build() }

    single { get<AppDatabase>().quoteDao() }

    single<IQuoteRepository> {
        QuoteRepository(
            get()
        )
    }

    single<IQuoteInteractor> {
        QuoteInteractor(
            get()
        )
    }

    viewModel { QuotePresenter(get()) }
}
