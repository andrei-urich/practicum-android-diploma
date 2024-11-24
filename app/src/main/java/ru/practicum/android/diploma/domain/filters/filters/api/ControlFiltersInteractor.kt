package ru.practicum.android.diploma.domain.filters.filters.api

import ru.practicum.android.diploma.domain.filters.Filters
import ru.practicum.android.diploma.domain.filters.area.model.AreaFilterModel
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel

interface ControlFiltersInteractor {
    fun saveFilterConfiguration(filters: Filters)
    fun getFiltersConfiguration(): Filters
    fun saveAreaCityFilter(newArea: AreaFilterModel, newCity: AreaFilterModel?)
    fun saveIndustryFilter(newIndustry: IndustryFilterModel)
    fun saveSalaryTargetFilter(newSalaryTarget: String)
    fun saveSalaryShowCheckFilter(newCheck: Boolean)
    fun fixFiltres()
    fun checkFiltresChanges(): Boolean
}
