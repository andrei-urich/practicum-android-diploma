package ru.practicum.android.diploma.ui.search

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.util.SalaryToString

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
        val title = vacancy.name + ", " + vacancy.area
        val salary = SalaryToString.getSalary(vacancy)
        binding.vacancyNameAndCityTVRecycler.text = title
        binding.companyNameTVRecycler.text = vacancy.employer
        binding.salaryTVRecycler.text = salary
    }
}
