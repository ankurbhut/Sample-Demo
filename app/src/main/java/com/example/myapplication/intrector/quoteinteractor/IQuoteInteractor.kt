package com.example.myapplication.intrector.quoteinteractor

import com.example.myapplication.domain.entities.Property
import io.reactivex.Observable

interface IQuoteInteractor {

    fun insert(quote: Property)

    fun update(quote: Property)

    fun delete(quote: Property)

    fun deleteAllQuotes()

    fun getAllQuotes(): Observable<Property>
}