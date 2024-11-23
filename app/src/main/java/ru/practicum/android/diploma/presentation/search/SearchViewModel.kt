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

class SearchViewModel(
    private val interactor: SearchInteractor
) : ViewModel() {

    private var searchText = EMPTY_STRING
    private var searchJob: Job? = null
    private var pages: Int = ZERO
    private var currentPage: Int = ZERO
    private var isNextPageLoading = false
    private var isNextPageLoadingError = false
    private val vacancyList: MutableList<VacancyShort> = mutableListOf()

    private val searchStateLiveData = MutableLiveData<Pair<SearchState, Int?>>()
    private val openTrigger = SingleEventLiveData<VacancyShort>()
    private val errorLoadingNextPageTrigger = SingleEventLiveData<Int>()

    fun getSearchStateLiveData(): LiveData<Pair<SearchState, Int?>> = searchStateLiveData
    fun getOpenTrigger(): LiveData<VacancyShort> = openTrigger
    fun getErrorLoadingNextPageTrigger(): LiveData<Int> = errorLoadingNextPageTrigger

    fun getSearchText(searchText: String) {
        if (searchText.isNotBlank() && this.searchText != searchText) {
            this.searchText = searchText
            searchDebounce()
        }
    }

    private fun request(state: SearchState, position: Int?) {
        if (!isNextPageLoading && !isNextPageLoadingError) {
            searchStateLiveData.postValue(Pair(state, position))
            isNextPageLoading = true
            viewModelScope.launch {
                interactor.search(
                    searchText,
                    currentPage
                ).collect { pair ->
                    when (pair.first) {
                        null -> {
                            isNextPageLoading = false
                            if (state is SearchState.Loading) {
                                searchStateLiveData.postValue(
                                    Pair(SearchState.LoadingError(pair.second), position)
                                )
                            } else {
                                isNextPageLoadingError = true
                                searchStateLiveData.postValue(
                                    Pair(SearchState.NextPageLoadingError(pair.second), position)
                                )
                            }
                        }

                        else -> {
                            val vacancies: List<VacancyShort> = pair.first as List<VacancyShort>
                            vacanciesAddAndLoadStatus(vacancies, position)
                        }
                    }
                }
            }
        }
    }

    private fun vacanciesAddAndLoadStatus(vacancies: List<VacancyShort>, position: Int?) {
        vacancyList.addAll(vacancies)
        isNextPageLoading = false
        currentPage++
        if (vacancies.isNotEmpty()) pages = vacancyList[0].pages
        searchStateLiveData.postValue(Pair(SearchState.Content(vacancyList), position))
    }


    fun getNextPage() {
        if (interactor.checkNet()) isNextPageLoadingError = false
        if (currentPage < pages) {
            val position = (currentPage - ONE) * PER_PAGE
            request(SearchState.LoadingNextPage, position)
        }
    }

    fun showVacancy(vacancy: VacancyShort) {
        openTrigger.postValue(vacancy)
    }

    private fun searchDebounce() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            pages = ZERO
            currentPage = ONE
            vacancyList.clear()
            delay(SEARCH_DEBOUNCE_DELAY)
            request(SearchState.Loading, null)
        }
    }

    fun clearScreen(flag: Boolean) {
        if (flag) searchStateLiveData.postValue(Pair(SearchState.Prepared, null))
    }

    private companion object {
        const val EMPTY_STRING = ""
        const val ONE = 1
        const val ZERO = 0
        const val SEARCH_DEBOUNCE_DELAY = 2000L
        const val PER_PAGE = 20
    }
}
