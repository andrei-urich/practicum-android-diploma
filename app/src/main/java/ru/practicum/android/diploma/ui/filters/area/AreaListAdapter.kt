package ru.practicum.android.diploma.ui.filters.area

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.AreaItemBinding

class AreaListAdapter(
    val areaList: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<AreaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        val binding =
            AreaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AreaViewHolder(binding) { position: Int ->
            if (position != RecyclerView.NO_POSITION) {
                areaList.getOrNull(position)?.let<String, Unit> { item ->
                    onItemClick(item)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        areaList.getOrNull(position)?.let<String, Unit> { item ->
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return areaList.size
    }
}
