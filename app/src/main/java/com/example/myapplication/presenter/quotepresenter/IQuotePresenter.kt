package com.example.myapplication.presenter.quotepresenter

import androidx.lifecycle.LiveData
import com.example.myapplication.domain.entities.Property
import com.example.myapplication.model.PropertyFilter

interface IQuotePresenter {

    fun insert(quote: Property)

    fun update(quote: Property)

    fun delete(quote: Property)

    fun deleteAllQuotes()

    fun getAllQuotes(): LiveData<Property>

    fun getAllApiQuotes(): LiveData<Property>

    fun getAllErrors(): LiveData<String>

}