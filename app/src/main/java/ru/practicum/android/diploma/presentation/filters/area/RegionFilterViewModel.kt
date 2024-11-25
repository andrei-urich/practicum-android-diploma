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

class RegionFilterViewModel(
    private val interactor: AreaFilterInteractor,
    private val filtersInteractor: ControlFiltersInteractor
) : ViewModel() {
    private val regionsListState = MutableLiveData<Pair<List<Region>?, Int?>>()
    private val screenExitTrigger = SingleEventLiveData<Boolean>()
    private val regionsList = mutableListOf<Region>()

    fun getRegionsListState(): LiveData<Pair<List<Region>?, Int?>> = regionsListState
    fun getScreenExitTrigger(): LiveData<Boolean> = screenExitTrigger

    fun setRegion(region: Region) {
        val newRegion = AreaFilterModel(region.id, region.name)
        val country = getCountry(region.parentId)
        if (country != null) {
            filtersInteractor.saveAreaCityFilter(AreaFilterModel(country.id, country.name), newRegion)
        } else {
            filtersInteractor.saveAreaCityFilter(AreaFilterModel(EMPTY_STRING, EMPTY_STRING), newRegion)
        }
        exitScreen()
    }

    private fun getCountry(parentId: String?): Region? {
        for (item in regionsList) {
            if (item.parentId == parentId) return item
        }
        return null
    }

    fun getAreaList() {
        viewModelScope.launch {
            interactor.getAllRegions().collect { pair ->
                when (pair.first) {
                    null -> {
                        regionsListState.postValue(Pair(null, pair.second))
                    }

                    else -> {
                        regionsList.clear()
                        regionsList.addAll(pair.first as List<Region>)
                        regionsListState.postValue(Pair(pair.first, null))
                    }
                }
            }
        }
    }

    fun exitScreen() {
        screenExitTrigger.postValue(true)
    }

    private companion object {
        const val EMPTY_STRING = ""
    }
}
