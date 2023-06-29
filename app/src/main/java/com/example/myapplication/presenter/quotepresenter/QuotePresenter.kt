package com.example.myapplication.presenter.quotepresenter

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.intrector.quoteinteractor.IQuoteInteractor
import com.example.myapplication.domain.entities.Property
import com.example.myapplication.model.PropertyFilter
import com.example.myapplication.network.NetworkClient
import com.example.myapplication.network.NetworkInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class QuotePresenter(private val iQuoteInteractor: IQuoteInteractor) : IQuotePresenter, ViewModel() {

    private val TAG = "MainPresenter"
    private val allQuotes : MutableLiveData<Property> = MutableLiveData()
    private val allApiQuotes : MutableLiveData<Property> = MutableLiveData()
    private val allErrors : MutableLiveData<String> = MutableLiveData()

    override fun insert(quote: Property) {
        iQuoteInteractor.insert(quote)
    }

    override fun update(quote: Property) {
        iQuoteInteractor.update(quote)
    }

    override fun delete(quote: Property) {
        iQuoteInteractor.delete(quote)
    }

    override fun deleteAllQuotes() {
        iQuoteInteractor.deleteAllQuotes()
    }

    override fun getAllQuotes(): LiveData<Property> {
        return allQuotes
    }

    override fun getAllApiQuotes(): LiveData<Property> {
        return allApiQuotes
    }

    override fun getAllErrors(): LiveData<String> {
        return allErrors
    }

    @SuppressLint("CheckResult")
    fun loadQuotes() {
        iQuoteInteractor.getAllQuotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { quotes -> allQuotes.postValue(quotes) },
                { error -> Log.d("RxJava", "Error getting info from interactor into presenter") }
            )
    }


    private fun getObservable(): Observable<Property> {
        return NetworkClient.retrofit.create(NetworkInterface::class.java)
            .getPropertyFilter()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getObserver(): DisposableObserver<Property> {
        return object : DisposableObserver<Property>() {
            override fun onNext(propertyFilterResponse: Property) {
                allApiQuotes.postValue(propertyFilterResponse)
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                allErrors.postValue("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }
    }

    fun getPropertyFilters() {
        getObservable().subscribeWith(getObserver())
    }
}