package com.posse.android.softjet.view.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.posse.android.softjet.databinding.PersonCardLayoutBinding
import com.posse.android.softjet.model.data.Data
import com.posse.android.softjet.view.main.holder.PersonHolder

class Adapter(
    private var onListItemClickListener: OnListItemClickListener
) : RecyclerView.Adapter<PersonHolder>() {

    private val displayData: MutableList<Data> = mutableListOf()

    fun setSingleData(data: Data) {
        displayData.add(data)
        notifyItemInserted(displayData.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        displayData.clear()
        notifyDataSetChanged()
    }

    fun getData(): List<Data> = displayData

    override fun getItemCount(): Int = displayData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        val binding = PersonCardLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PersonHolder(binding, onListItemClickListener)
    }

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        holder.bind(displayData[position])
    }

    fun interface OnListItemClickListener {
        fun onItemClick(data: Data)
    }
}
