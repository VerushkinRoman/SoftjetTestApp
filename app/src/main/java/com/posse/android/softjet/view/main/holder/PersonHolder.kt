package com.posse.android.softjet.view.main.holder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.posse.android.softjet.databinding.PersonCardLayoutBinding
import com.posse.android.softjet.model.data.Data
import com.posse.android.softjet.view.main.adapter.Adapter

class PersonHolder(
    private val binding: PersonCardLayoutBinding,
    private val listener: Adapter.OnListItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Data) = with(binding) {
        name.text = data.first_name
        surname.text = data.last_name
        email.text = data.email
        avatar.load(data.avatar) {
            placeholder(android.R.drawable.ic_menu_gallery)
            error(android.R.drawable.ic_menu_gallery)
        }

        root.setOnClickListener {
            listener.onItemClick(data)
        }
    }
}