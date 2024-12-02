package ru.practicum.android.diploma.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.search.SearchFiltersInteractor
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.presentation.SingleEventLiveData

class SearchViewModel(
    private val interactor: SearchInteractor,
    private val searchFiltersInteractor: SearchFiltersInteractor
) : ViewModel() {

    private var searchText = EMPTY_STRING
    private var searchJob: Job? = null
    private var pages: Int = ZERO
    private var currentPage: Int = ZERO
    private var position: Int = ZERO
    private var noNextPageLoading = true
    private var noNextPageLoadingError = true
    private var searchIsForcedByFilters = false
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
        if (searchText.isNotBlank() && (this.searchText != searchText || searchIsForcedByFilters)) {
            currentPage = ZERO
            pages = ZERO
            position = ZERO
            vacancyList.clear()
            this.searchText = searchText
            if (searchIsForcedByFilters) {
                searchStateLiveData.postValue(SearchState.Loading)
                searchDebounce(SEARCH_DEBOUNCE_DELAY_FORCED)
            } else {
                searchDebounce(SEARCH_DEBOUNCE_DELAY)
            }
            searchIsForcedByFilters = false
        }
    }

    private fun request(state: SearchState) {
        if (noNextPageLoading && noNextPageLoadingError) {
            searchStateLiveData.postValue(state)
            noNextPageLoading = false
            viewModelScope.launch {
                interactor.search(
                    searchText,
                    currentPage
                ).collect { pair ->
                    when (pair.first) {
                        null -> {
                            noNextPageLoading = true
                            if (state is SearchState.Loading) {
                                searchStateLiveData.postValue(SearchState.LoadingError(pair.second))

                            } else {
                                noNextPageLoadingError = false
                                searchStateLiveData.postValue(
                                    SearchState.Content(vacancyList)
                                )
                                positionNewPageToScroll.postValue(vacancyList.size - ONE)
                                errorLoadingNextPageTrigger.postValue(pair.second)
                            }
                        }

                        else -> {
                            val vacancies: List<VacancyShort> = pair.first as List<VacancyShort>
                            vacancyList.addAll(vacancies)
                            Log.d("MY", "______________________________________________________")
                            Log.d("MY", "запросили страницу ${currentPage}")
                            Log.d("MY", "нашлось элементов ${vacancies.size}")
                            Log.d("MY", "!!!!!!!")
                            Log.d("MY", "нашлось элементов ${vacancies.toString()}")
                            Log.d("MY", "!!!!!!!")
//                           Log.d("MY", "нашлось ВСЕГО ${vacancyList.get(0).found}")
//                            Log.d("MY", "нашлось страниц ${vacancyList.get(0).pages}")
//                            Log.d("MY", "показывается страница ${vacancyList.get(0).page}")

                            noNextPageLoading = true
                            currentPage++

                            Log.d("MY", "новая страница ${currentPage}")

                            searchStateLiveData.postValue(SearchState.Content(vacancyList))
                            positionNewPageToScroll.postValue(position)
                        }
                    }
                }
            }
        }
    }

    fun getNextPage() {
        if (vacancyList.size > position) {
            pages = vacancyList[position].pages
            Log.d("MY", "по новым данным нашлось страниц ${pages}")
            Log.d("MY", "нашлось ВСЕГО ${vacancyList[position].found}")
            Log.d("MY", "нашлось страниц ${vacancyList[position].pages}")
            Log.d("MY", "показывается страница ${currentPage-1}")

        } else {
            pages = vacancyList[ZERO].pages
        }
        if (interactor.checkNet()) noNextPageLoadingError = true
        if (currentPage < pages) {
         //   position = (currentPage - ONE) * PER_PAGE
            position = (currentPage) * PER_PAGE
            request(SearchState.LoadingNextPage)
        }
    }

    fun showVacancy(vacancy: VacancyShort) {
        openTrigger.postValue(vacancy)
    }

    private fun searchDebounce(delay: Long) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(delay)
            request(SearchState.Loading)
        }
    }

    fun clearScreen(flag: Boolean) {
        if (flag) {
            searchStateLiveData.postValue(SearchState.Prepared)
            searchJob?.cancel()
        }
    }

    fun checkFiltersNotEmpty(): Boolean {
        return searchFiltersInteractor.isFiltersNotEmpty()
    }

    fun isSearchForced(): Boolean {
        searchIsForcedByFilters = searchFiltersInteractor.isSearchForced()
        return searchIsForcedByFilters
    }

    private companion object {
        const val EMPTY_STRING = ""
        const val ONE = 1
        const val ZERO = 0
        const val SEARCH_DEBOUNCE_DELAY = 2000L
        const val SEARCH_DEBOUNCE_DELAY_FORCED = 0L
        const val PER_PAGE = 20
    }
}
