package ru.practicum.android.diploma.ui.search

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.util.Formatter

class VacancyViewHolder(
    private val binding: VacancyItemBinding,
    onItemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener {
            onItemClick(bindingAdapterPosition)
        }
    }

    private val vacancyCornerRadius: Int
        get() = itemView.context.resources.getDimensionPixelSize(R.dimen.vacancy_corner_radius)

    fun bind(vacancy: VacancyShort) {
        val title = vacancy.name + ", " + vacancy.area
        val salary = Formatter.getSalary(vacancy)
        binding.vacancyNameAndCityTVRecycler.text = title
        binding.companyNameTVRecycler.text = vacancy.employer
        binding.salaryTVRecycler.text = salary

        Glide.with(binding.employerLogoIVRecycler)
            .load(Uri.parse(vacancy.logoLink))
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .transform(CenterCrop(), RoundedCorners(vacancyCornerRadius))
            .into(binding.employerLogoIVRecycler)
    }
}
