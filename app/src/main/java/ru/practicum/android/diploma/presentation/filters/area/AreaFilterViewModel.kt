package ru.practicum.android.diploma.presentation.filters.area

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filters.area.model.AreaFilterModel
import ru.practicum.android.diploma.domain.filters.filters.api.ControlFiltersInteractor

class AreaFilterViewModel(
    val interactor: ControlFiltersInteractor
) : ViewModel() {
    private var currentCountry = AreaFilterModel()
    private var currentRegion = AreaFilterModel()
    private val filterValueLiveData = MutableLiveData<Pair<String, String>>()
    fun getFilterValueLiveData(): LiveData<Pair<String, String>> = filterValueLiveData

    init {
        getFilterSettings()
    }

    fun getFilterSettings() {
        val currentFilterSettings = interactor.getFiltersConfiguration()
        val settings = currentFilterSettings.getArea()
        currentCountry = settings.first
        currentRegion = settings.second
        filterValueLiveData.postValue(Pair(currentCountry.name, currentRegion.name))
    }


}
