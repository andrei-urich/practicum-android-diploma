package ru.practicum.android.diploma.data.filters.filters

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.filters.Filters
import ru.practicum.android.diploma.domain.filters.area.model.AreaFilterModel
import ru.practicum.android.diploma.domain.filters.filters.repository.FiltersControlRepository
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel

class FiltersControlRepositoryImpls(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : FiltersControlRepository {
    private var currentFilters: Filters = Filters()
    private var lastSavedFilters = Filters()
    private var forcedSearchFlag = false

    init {
        currentFilters =
            @Suppress("SwallowedException", "TooGenericExceptionCaught")
            try {
                gson.fromJson(
                    sharedPreferences.getString(FILTERS_ACTIVE, EMPTY_STRING) ?: EMPTY_STRING,
                    Filters::class.java
                )
            } catch (e: Exception) {
                val filtersJson = gson.toJson(Filters())
                sharedPreferences.edit().putString(FILTERS_ACTIVE, filtersJson).apply()
                Filters()
            }
        lastSavedFilters = currentFilters
    }

    override fun isFiltersNotEmpty(): Boolean {
        getFilterConfiguration()
        return currentFilters.isFiltersNotEmpty()
    }

    override fun saveFiltersConfiguration(filters: Filters) {
        currentFilters = filters
        saveFiltersInSP(currentFilters)
    }

    override fun fixFilters() {
        lastSavedFilters = currentFilters
    }

    override fun checkFiltersChanges(): Boolean {
        return lastSavedFilters != currentFilters
    }

    override fun saveAreaCityFilter(area: AreaFilterModel, city: AreaFilterModel) {
        currentFilters = Filters.setNewAreaCity(currentFilters, area, city)
        saveFiltersInSP(currentFilters)
    }

    override fun saveIndustryFilter(industry: IndustryFilterModel) {
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
            gson.fromJson(
                sharedPreferences.getString(FILTERS_ACTIVE, EMPTY_STRING) ?: EMPTY_STRING,
                Filters::class.java
            )
        return currentFilters
    }

    private fun saveFiltersInSP(filters: Filters) {
        val filtersJson = gson.toJson(filters)
        sharedPreferences.edit().putString(FILTERS_ACTIVE, filtersJson).apply()
    }

    override fun forceSearch() {
        forcedSearchFlag = true
    }

    override fun isSearchForced(): Boolean {
        if (forcedSearchFlag) {
            forcedSearchFlag = false
            return true
        } else return false
    }

    companion object {
        private const val FILTERS_ACTIVE = "FILTERS_ACTIVE"
        private const val EMPTY_STRING = ""
    }
}
