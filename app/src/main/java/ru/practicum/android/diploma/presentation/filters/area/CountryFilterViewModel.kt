package ru.practicum.android.diploma.presentation.filters.area

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.area.AreaFilterInteractor
import ru.practicum.android.diploma.domain.filters.area.model.AreaFilterModel
import ru.practicum.android.diploma.domain.filters.area.model.Region
import ru.practicum.android.diploma.domain.filters.filters.api.ControlFiltersInteractor
import ru.practicum.android.diploma.presentation.SingleEventLiveData

class CountryFilterViewModel(
    private val interactor: AreaFilterInteractor,
    private val filtersInteractor: ControlFiltersInteractor
) : ViewModel() {

    private val countryListState = MutableLiveData<Pair<List<Region>?, Int?>>()
    private val screenExitTrigger = SingleEventLiveData<Boolean>()

    fun getCountryListState(): LiveData<Pair<List<Region>?, Int?>> = countryListState
    fun getScreenExitTrigger(): LiveData<Boolean> = screenExitTrigger

    fun setCountry(region: Region) {
        val newCountry = AreaFilterModel(region.id, region.name)
        filtersInteractor.saveAreaCityFilter(newCountry, AreaFilterModel())
        exitScreen()
    }

    fun getAreaList() {
        viewModelScope.launch {
            interactor.getCountriesList().collect { pair ->
                when (pair.first) {
                    null -> {
                        countryListState.postValue(Pair(null, pair.second))
                    }

                    else -> {

                        countryListState.postValue(Pair(pair.first, null))
                    }
                }
            }
        }
    }

    fun exitScreen() {
        screenExitTrigger.postValue(true)
    }
}
