package com.example.myapplication.repositories.quoterepository

import android.annotation.SuppressLint
import android.util.Log
import com.example.myapplication.domain.entities.Property
import com.example.myapplication.repositories.daos.PropertyFilterModelDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class QuoteRepository(private val quoteDao: PropertyFilterModelDao) :
    IQuoteRepository {

    @SuppressLint("CheckResult")
    override fun insert(quote: Property) {
        quoteDao.insert(quote)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d("RxJava", "Insert Success") },
                { Log.d("RxJava", "Insert Error") }
            )
    }

    @SuppressLint("CheckResult")
    override fun update(quote: Property) {
        quoteDao.update(quote)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d("RxJava", "Update Success") },
                { Log.d("RxJava", "Update Error") }
            )
    }

    @SuppressLint("CheckResult")
    override fun delete(quote: Property) {
        quoteDao.delete(quote)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d("RxJava", "Delete Success") },
                { Log.d("RxJava", "Delete Error") }
            )
    }

    @SuppressLint("CheckResult")
    override fun deleteAllQuotes() {
        Completable.fromAction{ quoteDao.deleteAllNotes() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d("RxJava", "Delete all Success") },
                { Log.d("RxJava", "Delete all Error") }
            )
    }

    override fun getAllQuotes(): Observable<Property> = quoteDao.getAllNotes()
}