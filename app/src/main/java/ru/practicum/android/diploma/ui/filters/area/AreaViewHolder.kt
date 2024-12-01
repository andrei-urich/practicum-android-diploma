package ru.practicum.android.diploma.ui.filters.area

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.AreaItemBinding
import ru.practicum.android.diploma.domain.filters.area.model.Region

class AreaViewHolder(
    private val binding: AreaItemBinding,
    onItemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener {
            onItemClick(bindingAdapterPosition)
        }
    }

    fun bind(region: Region) {
        binding.areaName.text = region.name
    }

}
