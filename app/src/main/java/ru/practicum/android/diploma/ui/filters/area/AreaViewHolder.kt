package ru.practicum.android.diploma.ui.filters.area

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.AreaItemBinding

class AreaViewHolder(
    private val binding: AreaItemBinding,
    onItemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener {
            onItemClick(bindingAdapterPosition)
        }
    }

    fun bind(area: String) {
        binding.areaName.text = area
    }

}
