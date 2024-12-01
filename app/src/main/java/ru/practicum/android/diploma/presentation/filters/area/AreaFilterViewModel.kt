package ru.practicum.android.diploma.presentation.filters.area

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filters.area.model.AreaFilterModel
import ru.practicum.android.diploma.domain.filters.filters.api.ControlFiltersInteractor
import ru.practicum.android.diploma.presentation.SingleEventLiveData

class AreaFilterViewModel(
    val interactor: ControlFiltersInteractor
) : ViewModel() {
    private var currentCountry = AreaFilterModel()
    private var currentRegion = AreaFilterModel()

    private val filterValueLiveData = MutableLiveData<Pair<String, String>>()
    private val screenExitTrigger = SingleEventLiveData<Boolean>()
    private val buttonChoiceVisibilityLiveData = MutableLiveData<Boolean>()
    fun getFilterValueLiveData(): LiveData<Pair<String, String>> = filterValueLiveData
    fun getScreenExitTrigger(): LiveData<Boolean> = screenExitTrigger
    fun getButtonChoiceVisibilityLiveData(): LiveData<Boolean> = buttonChoiceVisibilityLiveData

    init {
        getFilterSettings()
        buttonChoiceVisibilityLiveData.postValue(false)
    }

    fun getFilterSettings() {
        val currentFilterSettings = interactor.getFiltersConfiguration()
        val settings = currentFilterSettings.getArea()
        currentCountry = settings.first
        currentRegion = settings.second
        filterValueLiveData.postValue(Pair(currentCountry.name, currentRegion.name))
        checkButtonChoiceVisibility()
    }

    private fun checkButtonChoiceVisibility() {
        if (currentCountry.name.isBlank()) {
            buttonChoiceVisibilityLiveData.postValue(false)
        } else {
            buttonChoiceVisibilityLiveData.postValue(
                true
            )
        }
    }

    fun clearCountry() {
        currentCountry = AreaFilterModel()
        currentRegion = AreaFilterModel()
        interactor.saveAreaCityFilter(currentCountry, currentRegion)
        filterValueLiveData.postValue(Pair(currentCountry.name, currentRegion.name))
        buttonChoiceVisibilityLiveData.postValue(false)
    }

    fun clearRegion() {
        currentRegion = AreaFilterModel()
        filterValueLiveData.postValue(Pair(currentCountry.name, currentRegion.name))
    }
    fun setFilter() {
        interactor.saveAreaCityFilter(currentCountry, currentRegion)
        exitScreen()
    }

    private fun exitScreen() {
        screenExitTrigger.postValue(true)
    }
}
