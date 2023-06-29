package com.example.myapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.listener.IItemClickListener
import com.example.myapplication.databinding.ItemPropertyFacilitiesBinding
import com.example.myapplication.model.Facility
import com.example.myapplication.model.Option
import com.example.myapplication.utility.hide
import com.example.myapplication.utility.isGone
import com.example.myapplication.utility.isVisible
import com.example.myapplication.utility.show

internal class PropertyFacilitiesAdapter(
    private var mArrayList: ArrayList<Facility>,
    private val mListener: IItemClickListener<Facility>?
) : RecyclerView.Adapter<PropertyFacilitiesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPropertyFacilitiesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mArrayList[position]
        Log.e("Position", position.toString())
        if (position == 0) {
            data.isSelected = true
        }
        holder.onBind(data)
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    fun getItems(): ArrayList<Facility> {
        return mArrayList
    }

    fun getItem(position: Int): Facility {
        return mArrayList[position]
    }

    fun updateItems(myList: ArrayList<Facility>) {
        mArrayList = myList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mBinding: ItemPropertyFacilitiesBinding) :
        RecyclerView.ViewHolder(mBinding.root), View.OnClickListener {

        fun onBind(data: Facility) {
            mBinding.data = data
            mBinding.executePendingBindings()
            setAdapter(data)

            if (data.isSelected) {
                mBinding.rvOption.show()
                mBinding.ivArrow.setImageResource(R.drawable.ic_arrow_up)
            } else {
                mBinding.rvOption.hide()
                mBinding.ivArrow.setImageResource(R.drawable.ic_arrow_down)
            }
        }

        private fun setAdapter(data: Facility) {
            if (mBinding.adapterOption == null) {
                mBinding.adapterOption = FacilitiesOptionAdapter(data.options, object : IItemClickListener<Option> {
                    override fun onItemClick(
                        view: View?,
                        position: Int,
                        actionType: Int?,
                        data: Option?
                    ) {

                    }
                })
            } else {
                mBinding.adapterOption?.updateItems(data.options)
            }
        }

        override fun onClick(view: View) {
            mListener?.onItemClick(
                view,
                absoluteAdapterPosition,
                IItemClickListener.CLICK,
                mArrayList[absoluteAdapterPosition]
            )
        }

        init {
            mBinding.linearFacility.setOnClickListener(this)
        }
    }
}