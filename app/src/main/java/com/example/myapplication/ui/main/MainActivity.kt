package com.example.myapplication.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.adapter.PropertyFacilitiesAdapter
import com.example.myapplication.adapter.listener.IItemClickListener
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.Facility
import com.example.myapplication.model.PropertyFilterModel

class MainActivity : AppCompatActivity(), MainViewInterface, IItemClickListener<Facility> {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private var mainPresenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMVP()
        setupViews()
        getMovieList()
    }

    private fun setupMVP() {
        mainPresenter = MainPresenter(this)
    }

    private fun setupViews() {
//        binding.rvPropertyFilter.layoutManager = LinearLayoutManager.
    }

    private fun getMovieList() {
        mainPresenter?.getPropertyFilters()
    }

    override fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE;
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE;
    }

    override fun displayPropertyFilters(propertyFilterResponse: PropertyFilterModel?) {
        if(propertyFilterResponse!=null) {
            if (binding.adapterFilter == null) {
                binding.adapterFilter = PropertyFacilitiesAdapter(
                    propertyFilterResponse.facilities, this
                )
            } else {
                binding.adapterFilter?.updateItems(propertyFilterResponse.facilities)
            }
        }else{
            Log.d(TAG,"Movies response null");
        }
    }

    override fun displayError(error: String) {
        showToast(error)
    }

    override fun onItemClick(view: View?, position: Int, actionType: Int?, data: Facility?) {
        if (data == null) return
        val currentList = binding.adapterFilter?.getItems()
        currentList?.get(position)?.isSelected = !data.isSelected
        currentList?.let { binding.adapterFilter?.updateItems(it) }
    }
}