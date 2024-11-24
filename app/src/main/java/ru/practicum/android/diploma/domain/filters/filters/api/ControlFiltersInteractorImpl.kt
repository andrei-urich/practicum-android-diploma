package ru.practicum.android.diploma.domain.filters.filters.api

import ru.practicum.android.diploma.domain.filters.Filters
import ru.practicum.android.diploma.domain.filters.filters.repository.FiltersControlRepository
import ru.practicum.android.diploma.domain.search.SearchFiltersInteractor

class ControlFiltersInteractorImpl(
    private val filtersControlRepository: FiltersControlRepository
) : ControlFiltersInteractor, SearchFiltersInteractor {
    override fun isFiltersNotEmpty(): Boolean {
        return filtersControlRepository.isFiltersNotEmpty()
    }

    override fun saveFilterConfiguration(filters: Filters) {
        filtersControlRepository.saveFiltersConfiguration(filters)
    }

    override fun getFiltersConfiguration(): Filters {
        return filtersControlRepository.getFilterConfiguration()
    }

    override fun saveAreaCityFilter(area: String, city: String?) {
        filtersControlRepository.saveAreaCityFilter(area, city)
    }

    override fun saveIndustryFilter(industry: String) {
        filtersControlRepository.saveIndustryFilter(industry)
    }

    override fun saveSalaryTargetFilter(newSalaryTarget: String) {
        val newSalaryTargetInt =
            if (newSalaryTarget.isNotEmpty() && newSalaryTarget.toInt() > 0) newSalaryTarget.toInt() else -1
        filtersControlRepository.saveSalaryTargetFilter(newSalaryTargetInt)
    }

    override fun saveSalaryShowCheckFilter(newCheck: Boolean) {
        filtersControlRepository.saveSalaryShowCheckFilter(newCheck)
    }
}
