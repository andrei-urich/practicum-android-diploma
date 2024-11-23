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
    private var position: Int = ZERO
    private var isNextPageLoading = false
    private var isNextPageLoadingError = false
    private var isNextPageCanBeLoad = (!isNextPageLoading && !isNextPageLoadingError)
    private val vacancyList: MutableList<VacancyShort> = mutableListOf()

    private val searchStateLiveData = MutableLiveData<SearchState>()
    private val openTrigger = SingleEventLiveData<VacancyShort>()
    private val errorLoadingNextPageTrigger = SingleEventLiveData<Int>()
    private val positionNewPageToScroll = SingleEventLiveData<Int>()

    fun getSearchStateLiveData(): LiveData<SearchState> = searchStateLiveData
    fun getOpenTrigger(): LiveData<VacancyShort> = openTrigger
    fun getErrorLoadingNextPageTrigger(): LiveData<Int> = errorLoadingNextPageTrigger
    fun getPositionNewPageToScroll(): LiveData<Int> = positionNewPageToScroll

    fun getSearchText(searchText: String) {
        if (searchText.isNotBlank() && this.searchText != searchText) {
            this.searchText = searchText
            searchDebounce()
        }
    }

    private fun request(state: SearchState) {
        if (isNextPageCanBeLoad) {
            searchStateLiveData.postValue(state)
            isNextPageLoading = true
            viewModelScope.launch {
                interactor.search(
                    searchText, currentPage
                ).collect { pair ->
                    when (pair.first) {
                        null -> {
                            isNextPageLoading = false
                            if (state is SearchState.Loading) {
                                searchStateLiveData.postValue(SearchState.LoadingError(pair.second))

                            } else {
                                isNextPageLoadingError = true
                                searchStateLiveData.postValue(
                                    SearchState.Content(vacancyList)
                                )
                                positionNewPageToScroll.postValue((vacancyList.size - ONE))
                                errorLoadingNextPageTrigger.postValue(pair.second)
                            }
                        }

                        else -> {
                            val vacancies: List<VacancyShort> = pair.first as List<VacancyShort>
                            vacancyList.addAll(vacancies)
                            isNextPageLoading = false
                            currentPage++
                            searchStateLiveData.postValue(SearchState.Content(vacancyList))
                            positionNewPageToScroll.postValue((position))
                        }
                    }
                }
            }
        }
    }

    fun getNextPage() {
        if (vacancyList.size >= position) pages = vacancyList[position].pages
        if (interactor.checkNet()) isNextPageLoadingError = false
        if (currentPage < pages) {
            position = (currentPage - ONE) * PER_PAGE
            request(SearchState.LoadingNextPage)
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
            position = ZERO
            vacancyList.clear()
            delay(SEARCH_DEBOUNCE_DELAY)
            request(SearchState.Loading)
        }
    }

    fun clearScreen(flag: Boolean) {
        if (flag) searchStateLiveData.postValue(SearchState.Prepared)
    }

    private companion object {
        const val EMPTY_STRING = ""
        const val ONE = 1
        const val ZERO = 0
        const val SEARCH_DEBOUNCE_DELAY = 2000L
        const val PER_PAGE = 20
    }
}
