package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.presentation.SingleEventLiveData
import ru.practicum.android.diploma.util.CLICK_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.EMPTY_STRING
import ru.practicum.android.diploma.util.SEARCH_DEBOUNCE_DELAY

class SearchViewModel(
    private val interactor: SearchInteractor,
) : ViewModel() {

    private var isClickAllowed = true
    private var searchText = EMPTY_STRING
    private var searchJob: Job? = null
    private var searchStateLiveData = MutableLiveData<SearchState>()
    private var openTrigger = SingleEventLiveData<VacancyShort>()

    fun getSearchStateLiveData(): LiveData<SearchState> = searchStateLiveData
    fun getOpenTrigger(): LiveData<VacancyShort> = openTrigger
    fun getSearchText(searchText: String) {
        this.searchText = searchText
        if (searchText.isNotBlank()) {
            searchDebounce(searchText)
        }
    }

    private fun request(request: String) {
        if (request.isNotEmpty()) {
            searchStateLiveData.postValue(SearchState.Loading)
            viewModelScope.launch {
                interactor.search(
                    request
                ).collect { pair ->
                    when (pair.first) {
                        null -> searchStateLiveData.postValue(
                            SearchState.Error
                        )

                        else -> {
                            val vacancies: List<VacancyShort> = pair.first as List<VacancyShort>
                            searchStateLiveData.postValue(SearchState.Content(vacancies))
                        }
                    }
                }
            }
        }
    }

    fun showVacancy(vacancy: VacancyShort) {
        if (clickDebounce()) {
            viewModelScope.launch {
                openTrigger.postValue(vacancy)
            }
        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

    private fun searchDebounce(searchText: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            request(searchText)
        }
    }
}
