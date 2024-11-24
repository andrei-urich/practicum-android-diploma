package ru.practicum.android.diploma.ui.filters.industry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryChooserItemBinding
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel

class IndustryFilterAdapter(private val industriesClickListener: IndustryClickListener) :
    RecyclerView.Adapter<IndustryFilterViewHolder>() {

    fun interface IndustryClickListener {
        fun onClick(item: IndustryFilterModel)
    }

    private var industries: List<IndustryFilterModel> = emptyList()

    fun setItems(items: List<IndustryFilterModel>, industrySelected: IndustryFilterModel?) {
        industries = items
        selectedIndustry = industrySelected
        notifyDataSetChanged()
    }

    private var selectedIndustry: IndustryFilterModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryFilterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return IndustryFilterViewHolder(
            industriesClickListener,
            IndustryChooserItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return industries.size
    }

    override fun onBindViewHolder(holder: IndustryFilterViewHolder, position: Int) {
        val industry = industries[position]
        holder.bind(industry, industry == selectedIndustry)
    }

    fun selectIndustry(industry: IndustryFilterModel) {
        val previousSelected = selectedIndustry
        selectedIndustry = if (selectedIndustry == industry) null else industry
        previousSelected?.let { notifyItemChanged(industries.indexOf(it)) }
        notifyItemChanged(industries.indexOf(industry))
    }
}
