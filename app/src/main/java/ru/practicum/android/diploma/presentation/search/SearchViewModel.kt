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
import ru.practicum.android.diploma.util.FIRST
import ru.practicum.android.diploma.util.NEXT
import ru.practicum.android.diploma.util.PER_PAGE
import ru.practicum.android.diploma.util.PREVIOUS
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

    fun getSearchStateLiveData(): LiveData<SearchState> = searchStateLiveData
    fun getOpenTrigger(): LiveData<VacancyShort> = openTrigger

    fun getSearchText(searchText: String) {
        if (searchText.isNotBlank() && this.searchText != searchText) {
            this.searchText = searchText
            searchDebounce(searchText)
        }
    }

    private fun request(searchText: String) {
        if (searchText.isNotEmpty()) {
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
                            updateSearchCounts(vacancies)
                            searchStateLiveData.postValue(SearchState.Content(vacancies))
                        }
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

    fun getNextPage(flag: String) {
        when (flag) {
            NEXT -> {
                currentPage++
                if (currentPage < pages) request(searchText)
            }

            PREVIOUS -> {
                currentPage--
                if (currentPage > FIRST) {
                    val startIndex = PER_PAGE * currentPage
                    val lastIndex = startIndex + PER_PAGE
                    val vacancyPage: List<VacancyShort> = vacancyList.subList(startIndex, lastIndex)
                    searchStateLiveData.postValue(SearchState.Content(vacancyPage))
                }
            }

            else -> {
                println()
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
            prepareSearchCounts()
            delay(SEARCH_DEBOUNCE_DELAY)
            request(searchText)
        }
    }

    private fun prepareSearchCounts() {
        pages = ZERO
        currentPage = FIRST
        vacancyList.clear()
    }

    private fun updateSearchCounts(vacancies: List<VacancyShort>) {
        vacancyList.addAll(vacancies)
        pages = vacancyList[0].pages
        currentPage = vacancyList[0].page
    }
}
