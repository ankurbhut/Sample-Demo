package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.listener.IItemClickListener
import com.example.myapplication.databinding.ItemOptionBinding
import com.example.myapplication.databinding.ItemPropertyFacilitiesBinding
import com.example.myapplication.model.Option
import com.example.myapplication.utility.hide
import com.example.myapplication.utility.show

internal class FacilitiesOptionAdapter(
    private var mArrayList: ArrayList<Option>,
    private val mListener: IItemClickListener<Option>?
) : RecyclerView.Adapter<FacilitiesOptionAdapter.ViewHolder>() {

    var lastSelectedItem = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mArrayList[position]
        holder.onBind(data)
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    fun getItems(): ArrayList<Option> {
        return mArrayList
    }

    fun getItem(position: Int): Option {
        return mArrayList[position]
    }

    fun updateItems(myList: ArrayList<Option>) {
        mArrayList = myList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mBinding: ItemOptionBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun onBind(data: Option) {
            mBinding.data = data
            mBinding.executePendingBindings()

            if (lastSelectedItem == absoluteAdapterPosition) {
                mBinding.linearOption.isSelected = true
            } else {
                mBinding.linearOption.isSelected = false
            }

            mBinding.linearOption.setOnClickListener {
                if (lastSelectedItem == -1) {
                    lastSelectedItem = absoluteAdapterPosition
                    mListener?.onItemClick(
                        it,
                        absoluteAdapterPosition,
                        IItemClickListener.CLICK,
                        mArrayList[absoluteAdapterPosition]
                    )
                }
            }
        }


    }
}