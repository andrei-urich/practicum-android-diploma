package ru.practicum.android.diploma.domain.filters.filters.repository

import ru.practicum.android.diploma.domain.filters.Filters
import ru.practicum.android.diploma.domain.filters.area.model.AreaFilterModel
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel

interface FiltersControlRepository {
    fun isFiltersNotEmpty(): Boolean
    fun saveFiltersConfiguration(filters: Filters)
    fun saveAreaCityFilter(area: AreaFilterModel, city: AreaFilterModel)
    fun saveIndustryFilter(industry: IndustryFilterModel)
    fun saveSalaryTargetFilter(newSalaryTarget: Int)
    fun saveSalaryShowCheckFilter(newCheck: Boolean)
    fun getFilterConfiguration(): Filters
    fun fixFilters()
    fun checkFiltersChanges(): Boolean
}
