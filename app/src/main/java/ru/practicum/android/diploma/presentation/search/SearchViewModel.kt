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
import ru.practicum.android.diploma.util.RESULT_CODE_BAD_REQUEST
import ru.practicum.android.diploma.util.SEARCH_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.ZERO

class SearchViewModel(
    private val interactor: SearchInteractor
) : ViewModel() {

    private var isClickAllowed = true
    private var searchText = EMPTY_STRING
    private var searchJob: Job? = null
    private var pages: Int = ZERO
    private var currentPage: Int = ZERO
    private var isNextPageLoading = false
    private var isNextPageLoadingError = false
    private val vacancyList: MutableList<VacancyShort> = mutableListOf()

    private val searchStateLiveData = MutableLiveData<Pair<SearchState, Int?>>()
    private val openTrigger = SingleEventLiveData<VacancyShort>()

    fun getSearchStateLiveData(): LiveData<Pair<SearchState, Int?>> = searchStateLiveData
    fun getOpenTrigger(): LiveData<VacancyShort> = openTrigger

    fun getSearchText(searchText: String) {
        if (searchText.isNotBlank() && this.searchText != searchText) {
            this.searchText = searchText
            searchDebounce()
        }
    }

    private fun request(state: SearchState, position: Int?) {
        when (state) {
            is SearchState.Loading, SearchState.LoadingNextPage -> {
                if (!isNextPageLoading && !isNextPageLoadingError) {
                    searchStateLiveData.postValue(Pair(state, position))
                    val request = constructRequest(searchText)
                    isNextPageLoading = true
                    viewModelScope.launch {
                        interactor.search(
                            request
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
                                    vacancyList.addAll(vacancies)
                                    isNextPageLoading = false
                                    currentPage++
                                    if (vacancies.isNotEmpty()) pages = vacancyList[0].pages
                                    searchStateLiveData.postValue(Pair(SearchState.Content(vacancyList), position))
                                }
                            }
                        }
                    }
                }
            }

            else -> {
                searchStateLiveData.postValue(
                    Pair(SearchState.LoadingError(RESULT_CODE_BAD_REQUEST), null)
                )
            }
        }
    }

    private fun constructRequest(searchText: String): HashMap<String, String> {
        val options: HashMap<String, String> = HashMap()
        options["text"] = searchText
        if (currentPage != 1) {
            options["page"] = currentPage.toString()
        }
        options["per_page"] = PER_PAGE.toString()
        return options
    }

    fun getNextPage() {
        if (interactor.checkNet()) isNextPageLoadingError = false
        if (currentPage < pages) {
            // Вычисляем позицию куда проскролить ресайклер, чтобы первой стояла первая вакансия с новой страницы
            val position = (currentPage - ONE) * PER_PAGE
            request(SearchState.LoadingNextPage, position)
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
}
