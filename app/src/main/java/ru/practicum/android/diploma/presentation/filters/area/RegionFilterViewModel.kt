package ru.practicum.android.diploma.presentation.filters.area

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.area.AreaFilterInteractor
import ru.practicum.android.diploma.domain.filters.area.model.AreaFilterModel
import ru.practicum.android.diploma.domain.filters.area.model.Region
import ru.practicum.android.diploma.domain.filters.filters.api.ControlFiltersInteractor
import ru.practicum.android.diploma.presentation.SingleEventLiveData
import java.util.Locale

class RegionFilterViewModel(
    private val interactor: AreaFilterInteractor,
    private val filtersInteractor: ControlFiltersInteractor
) : ViewModel() {

    private val regionsList = mutableListOf<Region>()
    private var country = AreaFilterModel()
    private var searchText = EMPTY_STRING
    private var searchJob: Job? = null
    private var currentCountry = AreaFilterModel()

    init {
        getFilterSettings()
    }

    private fun getFilterSettings() {
        val currentFilterSettings = filtersInteractor.getFiltersConfiguration()
        val settings = currentFilterSettings.getArea()
        currentCountry = settings.first
    }

    private val regionsListState = MutableLiveData<SearchAreaState>()
    private val screenExitTrigger = SingleEventLiveData<Boolean>()
    fun getRegionsListState(): LiveData<SearchAreaState> = regionsListState
    fun getScreenExitTrigger(): LiveData<Boolean> = screenExitTrigger

    fun getSearchText(searchText: String) {
        if (searchText.isNotBlank() && this.searchText != searchText) {
            this.searchText = searchText.trim().lowercase()
            searchDebounce(SEARCH_DEBOUNCE_DELAY)
        }
    }

    fun clearScreen(flag: Boolean) {
        if (flag) {
            regionsListState.postValue(SearchAreaState.Prepared)
            searchJob?.cancel()
        }
    }

    private fun searchDebounce(delay: Long) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(delay)
            search()
        }
    }

    private fun search() {
        if (regionsList.isNotEmpty()) {
            regionsListState.postValue(SearchAreaState.Loading)
            val regex = searchText.toRegex()
            val filteredList = regionsList.filter { region -> region.name.lowercase(Locale.ROOT).contains(regex) }
            if (filteredList.isNotEmpty()) {
                regionsListState.postValue(SearchAreaState.Content(filteredList))
            } else {
                regionsListState.postValue(SearchAreaState.Error(NOTHING_FOUND))
            }
        } else {
            regionsListState.postValue(SearchAreaState.Error(ERROR))
        }
    }


    fun setRegion(region: Region) {
        val newRegion = AreaFilterModel(region.id, region.name)
        if (currentCountry.id.isBlank()) {
            getCountry(region.parentId)
        } else {
            country = currentCountry
        }
        filtersInteractor.saveAreaCityFilter(country, newRegion)
        screenExitTrigger.postValue(true)
    }

    private fun getCountry(id: String?) {
        val startList = regionsList
        var parentId = id
        var flag = true
        while (flag) {
            val region = startList.find { region: Region -> region.id == parentId }
            if (region != null) {
                if (region.parentId == null) {
                    country = AreaFilterModel(region.id, region.name)
                    flag = false
                } else {
                    parentId = region.parentId
                }
            }
        }
    }

    fun getAreaList() {
        regionsListState.postValue(SearchAreaState.Loading)
        viewModelScope.launch {
            if (currentCountry.id.isBlank()) {
                interactor.getAllRegions().collect { pair ->
                    when (pair.first) {
                        null -> {
                            regionsListState.postValue(SearchAreaState.Error(pair.second))
                        }

                        else -> {
                            regionsList.clear()
                            regionsList.addAll(pair.first as List<Region>)
                            val filteredList = regionsList.filter { region ->
                                region.parentId != null
                            }
                            regionsListState.postValue(SearchAreaState.Content(filteredList))
                        }
                    }
                }
            } else {
                interactor.getInnerRegionsList(currentCountry.id).collect { pair ->
                    when (pair.first) {
                        null -> {
                            regionsListState.postValue(SearchAreaState.Error(pair.second))
                        }

                        else -> {
                            regionsList.clear()
                            regionsList.addAll(pair.first as List<Region>)
                            val filteredList = regionsList.filter { region ->
                                region.parentId != null
                            }
                            regionsListState.postValue(SearchAreaState.Content(filteredList))
                        }
                    }
                }
            }
        }
    }

    private companion object {
        const val EMPTY_STRING = ""
        const val SEARCH_DEBOUNCE_DELAY = 2000L
        const val NOTHING_FOUND = 0
        const val ERROR = 1
    }
}
