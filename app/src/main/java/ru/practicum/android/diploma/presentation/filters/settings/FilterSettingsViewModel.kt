package ru.practicum.android.diploma.presentation.filters.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filters.Filters
import ru.practicum.android.diploma.domain.filters.filters.api.ControlFiltersInteractor
import ru.practicum.android.diploma.domain.filters.industry.api.IndustryFilterInteractor
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel
import ru.practicum.android.diploma.domain.search.SearchFiltersInteractor

class FilterSettingsViewModel(
    private val controlFiltersInteractor: ControlFiltersInteractor,
    private val searchFiltersInteractor: SearchFiltersInteractor,
    private val industryFilterInteractor: IndustryFilterInteractor,
    private val filtersUIMapper: FiltersToFiltersUIMapper
) : ViewModel() {
    private var currentFilters = Filters()
    private val filtersConfigurationLiveData = MutableLiveData(FiltersUIModel())
    private val isFiltersOnLiveData = MutableLiveData(false)
    private val isFiltresChanged = MutableLiveData(false)

    fun getFiltersConfigurationLD(): LiveData<FiltersUIModel> = filtersConfigurationLiveData
    fun getIsFiltersOnLD(): LiveData<Boolean> = isFiltersOnLiveData
    fun getIsFiltresChangedLD(): LiveData<Boolean> = isFiltresChanged

    fun getFiltersConfiguration() {
        val industryCurrent = industryFilterInteractor.getIndustrySettings() ?: IndustryFilterModel()
        controlFiltersInteractor.saveIndustryFilter(industryCurrent)
        currentFilters = controlFiltersInteractor.getFiltersConfiguration()
        filtersConfigurationLiveData.postValue(filtersUIMapper.map(currentFilters))
        isFiltersOnLiveData.postValue(searchFiltersInteractor.isFiltersNotEmpty())
        checkFiltresChanges()
    }

    fun saveNewSalaryTarget(newSalaryTarget: String) {
        controlFiltersInteractor.saveSalaryTargetFilter(newSalaryTarget)
        isFiltersOnLiveData.postValue(searchFiltersInteractor.isFiltersNotEmpty())
        checkFiltresChanges()
    }

    fun saveSalaryShowCheckFilter(newCheck: Boolean) {
        controlFiltersInteractor.saveSalaryShowCheckFilter(newCheck)
        isFiltersOnLiveData.postValue(searchFiltersInteractor.isFiltersNotEmpty())
        checkFiltresChanges()
    }

    fun clearFilters() {
        industryFilterInteractor.deleteIndustrySettings()
        currentFilters = Filters()
        controlFiltersInteractor.saveFilterConfiguration(currentFilters)
        getFiltersConfiguration()
    }

    fun fixFiltres() {
        controlFiltersInteractor.fixFiltres()
    }

    private fun checkFiltresChanges() {
        isFiltresChanged.postValue(controlFiltersInteractor.checkFiltresChanges())
    }
    fun forceSearch(){
        controlFiltersInteractor.forceSearch()
    }

}
