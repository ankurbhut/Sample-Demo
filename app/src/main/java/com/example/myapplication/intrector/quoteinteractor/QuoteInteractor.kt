package com.example.myapplication.intrector.quoteinteractor

import com.example.myapplication.domain.entities.Property
import com.example.myapplication.repositories.quoterepository.IQuoteRepository
import io.reactivex.Observable

class QuoteInteractor(private val iQuoteRepository: IQuoteRepository) :
    IQuoteInteractor {

    override fun insert(quote: Property) {
        iQuoteRepository.insert(quote)
    }

    override fun update(quote: Property) {
        iQuoteRepository.update(quote)
    }

    override fun delete(quote: Property) {
        iQuoteRepository.delete(quote)
    }

    override fun deleteAllQuotes() {
        iQuoteRepository.deleteAllQuotes()
    }

    override fun getAllQuotes(): Observable<Property> {
        return iQuoteRepository.getAllQuotes()
    }
}