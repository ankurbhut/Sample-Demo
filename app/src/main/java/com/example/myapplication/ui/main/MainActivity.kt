package com.example.myapplication.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.adapter.PropertyFacilitiesAdapter
import com.example.myapplication.adapter.listener.IItemClickListener
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.domain.entities.Property
import com.example.myapplication.model.Facility
import com.example.myapplication.presenter.quotepresenter.QuotePresenter
import com.example.myapplication.utility.hide
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), IItemClickListener<Facility> {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    val quotePresenter: QuotePresenter by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserver()
        getMovieList()
    }

    private fun setupObserver() {
        quotePresenter.getAllQuotes()
            .observe(this) { propertyFilterResponse ->
                binding.progressBar.hide()
                Log.e("exclusion", propertyFilterResponse.exclusions[0][0].optionsId.toString())
                if (binding.adapterFilter == null) {
                    binding.adapterFilter = PropertyFacilitiesAdapter(
                        propertyFilterResponse.facilities, this
                    )
                } else {
                    binding.adapterFilter?.updateItems(propertyFilterResponse.facilities)
                }
            }

        quotePresenter.getAllApiQuotes()
            .observe(this) { propertyFilterResponse ->
                quotePresenter.insert(propertyFilterResponse)
                binding.progressBar.hide()
                if (binding.adapterFilter == null) {
                    binding.adapterFilter = PropertyFacilitiesAdapter(
                        propertyFilterResponse.facilities, this
                    )
                } else {
                    binding.adapterFilter?.updateItems(propertyFilterResponse.facilities)
                }
            }

        quotePresenter.getAllErrors()
            .observe(this) { error ->
                showToast(error)
            }
    }

    private fun getMovieList() {
        quotePresenter.getPropertyFilters()
        quotePresenter.loadQuotes()
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun onItemClick(view: View?, position: Int, actionType: Int?, data: Facility?) {
        if (data == null) return
        val currentList = binding.adapterFilter?.getItems()
        currentList?.get(position)?.isSelected = !data.isSelected
        currentList?.let { binding.adapterFilter?.updateItems(it) }
    }
}