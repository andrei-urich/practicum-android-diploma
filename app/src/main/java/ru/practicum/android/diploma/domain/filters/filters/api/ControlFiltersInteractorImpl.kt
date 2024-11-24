package ru.practicum.android.diploma.domain.filters.filters.api

import ru.practicum.android.diploma.domain.filters.Filters
import ru.practicum.android.diploma.domain.filters.area.model.AreaFilterModel
import ru.practicum.android.diploma.domain.filters.filters.repository.FiltersControlRepository
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel
import ru.practicum.android.diploma.domain.search.SearchFiltersInteractor

class ControlFiltersInteractorImpl(
    private val filtersControlRepository: FiltersControlRepository
) : ControlFiltersInteractor, SearchFiltersInteractor {
    override fun isFiltersNotEmpty(): Boolean {
        return filtersControlRepository.isFiltersNotEmpty()
    }

    override fun isSearchForced(): Boolean {
        return filtersControlRepository.isSearchForced()
    }

    override fun saveFilterConfiguration(filters: Filters) {
        filtersControlRepository.saveFiltersConfiguration(filters)
    }

    override fun getFiltersConfiguration(): Filters {
        return filtersControlRepository.getFilterConfiguration()
    }

    override fun saveAreaCityFilter(area: AreaFilterModel, city: AreaFilterModel?) {
        filtersControlRepository.saveAreaCityFilter(area, city ?: AreaFilterModel())
    }

    override fun saveIndustryFilter(industry: IndustryFilterModel) {
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

    override fun fixFiltres() {
        filtersControlRepository.fixFilters()
    }

    override fun checkFiltresChanges(): Boolean {
        return filtersControlRepository.checkFiltersChanges()
    }

    override fun forceSearch() {
        filtersControlRepository.forceSearch()
    }
}
