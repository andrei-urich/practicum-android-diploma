package ru.practicum.android.diploma.presentation.filters.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filters.Filters
import ru.practicum.android.diploma.domain.filters.area.model.AreaFilterModel
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
        checkIsFiltresOn()
        checkFiltresChanges()
    }

    fun saveNewSalaryTarget(newSalaryTarget: String) {
        controlFiltersInteractor.saveSalaryTargetFilter(newSalaryTarget)
        checkIsFiltresOn()
        checkFiltresChanges()
    }

    fun saveSalaryCheck(newCheck: Boolean) {
        controlFiltersInteractor.saveSalaryShowCheckFilter(newCheck)
        checkIsFiltresOn()
        checkFiltresChanges()
    }

    fun clearAreas() {
        controlFiltersInteractor.saveAreaCityFilter(AreaFilterModel(), AreaFilterModel())
        getFiltersConfiguration()
    }

    fun clearIndustry() {
        industryFilterInteractor.deleteIndustrySettings()
        controlFiltersInteractor.saveIndustryFilter(IndustryFilterModel())
        getFiltersConfiguration()
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

    private fun checkIsFiltresOn() {
        isFiltersOnLiveData.postValue(searchFiltersInteractor.isFiltersNotEmpty())
    }

    fun forceSearch() {
        controlFiltersInteractor.forceSearch()
    }

}
