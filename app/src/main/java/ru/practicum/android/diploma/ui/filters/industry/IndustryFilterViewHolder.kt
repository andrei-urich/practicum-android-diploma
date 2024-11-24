package ru.practicum.android.diploma.ui.filters.industry

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryChooserItemBinding
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel

class IndustryFilterViewHolder(
    private val clickListener: IndustryFilterAdapter.IndustryClickListener,
    private val binding: IndustryChooserItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: IndustryFilterModel, isSelected: Boolean) {
        binding.industryName.text = item.name
        itemView.setOnClickListener { clickListener.onClick(item) }
        binding.radioButton.isChecked = isSelected
        val clickHandler = View.OnClickListener {
            clickListener.onClick(item)
            binding.radioButton.isChecked = true
        }
        itemView.setOnClickListener(clickHandler)
        binding.radioButton.setOnClickListener(clickHandler)
    }
}
