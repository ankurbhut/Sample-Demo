package com.example.myapplication.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.adapter.PropertyFacilitiesAdapter
import com.example.myapplication.adapter.listener.IItemClickListener
import com.example.myapplication.adapter.listener.IItemSubClickListener
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.Exclusion
import com.example.myapplication.model.Facility
import com.example.myapplication.model.Option
import com.example.myapplication.presenter.propertypresenter.PropertyPresenter
import com.example.myapplication.utility.hide
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), IItemClickListener<Facility>,
    IItemSubClickListener<Option> {

    private lateinit var binding: ActivityMainBinding
    private val propertyPresenter: PropertyPresenter by viewModel()
    var exclusion: ArrayList<ArrayList<Exclusion>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserver()
        getProeprtyList()
    }

    private fun setupObserver() {
        propertyPresenter.getAllProperties()
            .observe(this) { propertyFilterResponse ->
                exclusion = propertyFilterResponse.exclusions
                binding.progressBar.hide()
                if (binding.adapterFilter == null) {
                    binding.adapterFilter = PropertyFacilitiesAdapter(
                        propertyFilterResponse.facilities, this, this
                    )
                } else {
                    binding.adapterFilter?.updateItems(propertyFilterResponse.facilities)
                }
            }

        propertyPresenter.getAllApiProperties()
            .observe(this) { propertyFilterResponse ->
                exclusion = propertyFilterResponse.exclusions
                propertyPresenter.insert(propertyFilterResponse)
                binding.progressBar.hide()
                if (binding.adapterFilter == null) {
                    binding.adapterFilter = PropertyFacilitiesAdapter(
                        propertyFilterResponse.facilities, this, this
                    )
                } else {
                    binding.adapterFilter?.updateItems(propertyFilterResponse.facilities)
                }
            }

        propertyPresenter.getAllErrors()
            .observe(this) { error ->
                showToast(error)
            }
    }

    private fun getProeprtyList() {
        propertyPresenter.getPropertyFilters()
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

    override fun onSubItemClick(
        view: View?,
        mainPosition: Int,
        position: Int,
        actionType: Int?,
        data: Option?
    ) {
        if (data == null) return
        if (exclusion.isEmpty()) return

        val currentList = binding.adapterFilter?.getItems() ?: return
        var facilityId = ""
        var optionId = ""

        when (mainPosition) {
            0 -> {
                for (item in 0 until exclusion.size) {
                    for (subItem in exclusion[item]) {
                        if ((subItem.facilityId?.toInt() ?: 0) >= (mainPosition + 1))
                            if (subItem.facilityId == currentList[mainPosition].facilityId && subItem.optionsId == currentList[mainPosition].options[position].id) {
                                facilityId = exclusion[item][1].facilityId ?: ""
                                optionId = exclusion[item][1].optionsId ?: ""
                            }
                    }
                }
            }
            1 -> {
                for (item in 0 until exclusion.size) {
                    for (subItem in exclusion[item]) {
                        if ((subItem.facilityId?.toInt() ?: 0) >= (mainPosition + 1))
                            if (subItem.facilityId == currentList[mainPosition].facilityId && subItem.optionsId == currentList[mainPosition].options[position].id) {
                                facilityId = exclusion[item][1].facilityId ?: ""
                                optionId = exclusion[item][1].optionsId ?: ""
                            }
                    }
                }
            }
            2 -> {
                for (item in 0 until exclusion.size) {
                    for (subItem in exclusion[item]) {
                        if ((subItem.facilityId?.toInt() ?: 0) >= (mainPosition + 1))
                            if (subItem.facilityId == currentList[mainPosition].facilityId && subItem.optionsId == currentList[mainPosition].options[position].id) {
                                facilityId = exclusion[item][1].facilityId ?: ""
                                optionId = exclusion[item][1].optionsId ?: ""
                            }
                    }
                }
            }
        }

        resetList(mainPosition, facilityId, optionId)

    }

    private fun resetList(mainPosition: Int, facilityId: String, optionId: String) {
        val currentList = binding.adapterFilter?.getItems() ?: return
        for (item in mainPosition+1 until currentList.size) {
            for (option in 0 until currentList[item].options.size) {
                currentList[item].options[option].isDisable = facilityId == currentList[item].facilityId && optionId == currentList[item].options[option].id
            }
        }
        currentList.let { binding.adapterFilter?.updateItems(it, mainPosition+1) }
    }
}