package ru.practicum.android.diploma.domain.filters.filters.api

import ru.practicum.android.diploma.domain.filters.Filters
import ru.practicum.android.diploma.domain.filters.filters.repository.FiltersControlRepository
import ru.practicum.android.diploma.domain.search.models.CheckFiltersUseCase

class ControlFiltersInteractorImpl(
    private val filtersControlRepository: FiltersControlRepository
) : ControlFiltersInteractor, CheckFiltersUseCase {
    override fun isFiltersOn(): Boolean {
        return filtersControlRepository.isFiltersOn()
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

    override fun saveSalaryTargetFilter(newSalaryTarget: Int) {
        filtersControlRepository.saveSalaryTargetFilter(newSalaryTarget)
    }

    override fun saveSalaryShowCheckFilter(newCheck: Boolean) {
        filtersControlRepository.saveSalaryShowCheckFilter(newCheck)
    }
}
