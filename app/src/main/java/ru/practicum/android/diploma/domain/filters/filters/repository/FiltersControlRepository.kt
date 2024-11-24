package ru.practicum.android.diploma.domain.filters.filters.repository

import ru.practicum.android.diploma.domain.filters.Filters

interface FiltersControlRepository {
    fun isFiltersOn(): Boolean
    fun saveFiltersConfiguration(filters: Filters)
    fun saveAreaCityFilter(area: String, city: String?)
    fun saveIndustryFilter(industry: String)
    fun saveSalaryTargetFilter(newSalaryTarget: Int)
    fun saveSalaryShowCheckFilter(newCheck: Boolean)
    fun getFilterConfiguration(): Filters
}
