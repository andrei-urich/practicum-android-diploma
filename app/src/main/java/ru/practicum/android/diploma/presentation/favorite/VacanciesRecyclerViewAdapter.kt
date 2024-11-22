package ru.practicum.android.diploma.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.search.models.VacancyShort

class VacanciesRecyclerViewAdapter(private val vacancyList: List<VacancyShort>) :
    RecyclerView.Adapter<VacanciesRecyclerViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacanciesRecyclerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.vacancy_item, parent, false)
        return VacanciesRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vacancyList.size
    }

    override fun onBindViewHolder(holder: VacanciesRecyclerViewHolder, position: Int) {
        holder.bind(vacancyList[position])
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, vacancyList[position])
        }
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    interface OnClickListener {
        fun onClick(position: Int, vacancy: VacancyShort)
    }
}
