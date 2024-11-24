package ru.practicum.android.diploma.presentation.filters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filters.Filters
import ru.practicum.android.diploma.domain.filters.filters.api.ControlFiltersInteractor
import ru.practicum.android.diploma.domain.search.SearchFiltersInteractor

class FilterSettingsViewModel(
    private val controlFiltersInteractor: ControlFiltersInteractor,
    private val searchFiltersInteractor: SearchFiltersInteractor
) : ViewModel() {
    private var currentFilters = Filters()
    private val filtersConfigurationLiveData = MutableLiveData(Filters())
    private val isFiltersOnLiveData = MutableLiveData(false)

    fun getFiltersConfigurationLD(): LiveData<Filters> = filtersConfigurationLiveData
    fun getIsFiltersOnLD(): LiveData<Boolean> = isFiltersOnLiveData

    fun getFiltersConfiguration() {
        currentFilters = controlFiltersInteractor.getFiltersConfiguration()
        filtersConfigurationLiveData.postValue(currentFilters)
        isFiltersOnLiveData.postValue(searchFiltersInteractor.isFiltersNotEmpty())
    }

    fun saveNewSalaryTarget(newSalaryTarget: String) {
        controlFiltersInteractor.saveSalaryTargetFilter(newSalaryTarget)
        isFiltersOnLiveData.postValue(searchFiltersInteractor.isFiltersNotEmpty())
    }

    fun saveSalaryShowCheckFilter(newCheck: Boolean) {
        controlFiltersInteractor.saveSalaryShowCheckFilter(newCheck)
        isFiltersOnLiveData.postValue(searchFiltersInteractor.isFiltersNotEmpty())
    }

    fun clearFilters() {
        currentFilters = Filters()
        controlFiltersInteractor.saveFilterConfiguration(currentFilters)
        getFiltersConfiguration()
    }
}
