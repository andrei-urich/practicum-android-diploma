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
import ru.practicum.android.diploma.util.ONE
import ru.practicum.android.diploma.util.PER_PAGE
import ru.practicum.android.diploma.util.SEARCH_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.ZERO

class SearchViewModel(
    private val interactor: SearchInteractor,
) : ViewModel() {

    private var isClickAllowed = true
    private var searchText = EMPTY_STRING
    private var searchJob: Job? = null
    private var searchStateLiveData = MutableLiveData<SearchState>()
    private var openTrigger = SingleEventLiveData<VacancyShort>()
    private val vacancyList: MutableList<VacancyShort> = mutableListOf()
    private var pages: Int = ZERO
    private var currentPage: Int = ZERO
    private var isNextPageLoading = false

    fun getSearchStateLiveData(): LiveData<SearchState> = searchStateLiveData
    fun getOpenTrigger(): LiveData<VacancyShort> = openTrigger

    fun getSearchText(searchText: String) {
        if (searchText.isNotBlank() && this.searchText != searchText) {
            this.searchText = searchText
            searchDebounce()
        }
    }

    private fun request() {
        searchStateLiveData.postValue(SearchState.Loading)
        val request = constructRequest(searchText)
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
                        vacancyList.addAll(vacancies)
                        updateSearchCounts()
                        searchStateLiveData.postValue(SearchState.Content(vacancyList))
                    }
                }
            }
        }
    }

    private fun constructRequest(searchText: String): HashMap<String, String> {
        val options: HashMap<String, String> = HashMap()
        options["text"] = searchText
        if (currentPage != 0) {
            options["page"] = currentPage.toString()
        }
        options["per_page"] = PER_PAGE.toString()
        return options
    }

    fun getNextPage() {
        currentPage++
        isNextPageLoading = true
        if (currentPage < pages) request()
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

    private fun searchDebounce() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            prepareSearchCounts()
            delay(SEARCH_DEBOUNCE_DELAY)
            request()
        }
    }

    private fun prepareSearchCounts() {
        pages = ZERO
        currentPage = ONE
        vacancyList.clear()
    }

    private fun updateSearchCounts() {
        pages = vacancyList[0].pages
        currentPage = vacancyList[0].page
        isNextPageLoading = false
    }
}
