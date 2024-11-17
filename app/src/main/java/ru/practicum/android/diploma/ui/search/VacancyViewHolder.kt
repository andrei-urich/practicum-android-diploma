package ru.practicum.android.diploma.ui.search

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.search.models.VacancyShort

class VacancyViewHolder(
    private val binding: VacancyItemBinding,
    onItemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener {
            onItemClick(bindingAdapterPosition)
        }
    }

    fun bind(vacancy: VacancyShort) {
        binding.vacancyNameAndCityTVRecycler.text = vacancy.name
        binding.companyNameTVRecycler.text = vacancy.vacancyId
    }
}
