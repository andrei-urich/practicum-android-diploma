package ru.practicum.android.diploma.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.search.models.VacancyShort

class SearchAdapter(
    val vacancies: List<VacancyShort>,
    private val onItemClick: (VacancyShort) -> Unit
) : RecyclerView.Adapter<VacancyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val binding =
            VacancyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VacancyViewHolder(binding) { position: Int ->
            if (position != RecyclerView.NO_POSITION) {
                vacancies.getOrNull(position)?.let<VacancyShort, Unit> { vacancy ->
                    onItemClick(vacancy)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        vacancies.getOrNull(position)?.let<VacancyShort, Unit> { vacancy ->
            holder.bind(vacancy)
        }
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }
}
