package com.example.myapplication.repositories.quoterepository

import com.example.myapplication.domain.entities.Property
import io.reactivex.Observable

interface IQuoteRepository {

    fun insert(quote: Property)

    fun update(quote: Property)

    fun delete(quote: Property)

    fun deleteAllQuotes()

    fun getAllQuotes(): Observable<Property>
}

