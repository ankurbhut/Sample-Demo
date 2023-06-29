package com.example.myapplication.ui.main

import com.example.myapplication.model.PropertyFilterModel


/**
 * Created by anujgupta on 26/12/17.
 */
interface MainViewInterface {
    fun showToast(message: String)
    fun showProgressBar()
    fun hideProgressBar()
    fun displayPropertyFilters(propertyFilterResponse: PropertyFilterModel?)
    fun displayError(error: String)
}