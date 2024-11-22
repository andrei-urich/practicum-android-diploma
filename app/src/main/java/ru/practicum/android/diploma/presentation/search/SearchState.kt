package ru.practicum.android.diploma.presentation.search

import ru.practicum.android.diploma.domain.search.models.VacancyShort

sealed class SearchState {
    object Prepared : SearchState()
    object Loading : SearchState()
    object LoadingNextPage : SearchState()
    data class LoadingError(
        val resultCode: Int?
    ) : SearchState()

    data class NextPageLoadingError(
        val resultCode: Int?
    ) : SearchState()

    data class Content(
        val vacancyList: List<VacancyShort>,
    ) : SearchState()
}
