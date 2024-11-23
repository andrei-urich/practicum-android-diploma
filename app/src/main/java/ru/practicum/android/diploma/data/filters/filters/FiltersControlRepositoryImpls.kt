package ru.practicum.android.diploma.data.filters.filters

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.FILTERS_ACTIVE
import ru.practicum.android.diploma.domain.filters.Filters
import ru.practicum.android.diploma.domain.filters.filters.repository.FiltersControlRepository

class FiltersControlRepositoryImpls(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : FiltersControlRepository {
    private var currentFilters: Filters = Filters()

    init {
        currentFilters =
            @Suppress("SwallowedException", "TooGenericExceptionCaught")
            try {
                gson.fromJson(sharedPreferences.getString(FILTERS_ACTIVE, "") ?: "", Filters::class.java)
            } catch (e: Exception) {
                Filters()
            }
    }

    override fun isFiltersOn(): Boolean {
        getFilterConfiguration()
        return currentFilters.isFiltersEmpty()
    }

    override fun saveFiltersConfiguration(filters: Filters) {
        currentFilters = filters
        saveFiltersInSP(currentFilters)
    }

    override fun saveAreaCityFilter(area: String, city: String?) {
        currentFilters = Filters.setNewAreaCity(currentFilters, area, city)
        saveFiltersInSP(currentFilters)
    }

    override fun saveIndustryFilter(industry: String) {
        currentFilters = Filters.setNewIndustry(currentFilters, industry)
        saveFiltersInSP(currentFilters)
    }

    override fun saveSalaryTargetFilter(newSalaryTarget: Int) {
        currentFilters = Filters.setNewSalaryTarget(currentFilters, newSalaryTarget)
        saveFiltersInSP(currentFilters)
    }

    override fun saveSalaryShowCheckFilter(newCheck: Boolean) {
        currentFilters = Filters.setNewSalaryShowCheck(currentFilters, newCheck)
        saveFiltersInSP(currentFilters)
    }

    override fun getFilterConfiguration(): Filters {
        currentFilters =
            gson.fromJson(sharedPreferences.getString(FILTERS_ACTIVE, "") ?: "", Filters::class.java)
        return currentFilters
    }

    private fun saveFiltersInSP(filters: Filters) {
        val filtersJson = gson.toJson(filters)
        sharedPreferences.edit().putString(FILTERS_ACTIVE, filtersJson).apply()
    }
}
