package ru.practicum.android.diploma.presentation.search

import ru.practicum.android.diploma.domain.search.models.VacancyShort

sealed class SearchState {
    data object Prepared : SearchState()
    data object Loading : SearchState()
    data object LoadingNextPage : SearchState()
    data class LoadingError(
        val resultCode: Int?
    ) : SearchState()

    data class Content(
        val vacancyList: List<VacancyShort>,
    ) : SearchState()
}
