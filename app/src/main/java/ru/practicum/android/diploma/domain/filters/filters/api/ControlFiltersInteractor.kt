package ru.practicum.android.diploma.domain.filters.filters.api

import ru.practicum.android.diploma.domain.filters.Filters

interface ControlFiltersInteractor {
    fun saveFilterConfiguration(filters: Filters)
    fun getFiltersConfiguration(): Filters
    fun saveAreaCityFilter(newArea: String, newCity: String?)
    fun saveIndustryFilter(newIndustry: String)
    fun saveSalaryTargetFilter(newSalaryTarget: String)
    fun saveSalaryShowCheckFilter(newCheck: Boolean)
}
