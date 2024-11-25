package ru.practicum.android.diploma.presentation.filters.area

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filters.area.model.Region

class RegionFilterViewModel : ViewModel() {
    fun setArea(region: Region) {
        println(region.name)
    }

    fun getAreaList() {
        println()
    }
}
