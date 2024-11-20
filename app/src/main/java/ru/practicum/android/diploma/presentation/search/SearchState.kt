package ru.practicum.android.diploma.presentation.search

import ru.practicum.android.diploma.domain.search.models.VacancyShort

sealed class SearchState {
    object Loading : SearchState()
    object LoadingNextPage : SearchState()
    data class Error(
        val resultCode: Int?
    ) : SearchState()

    data class Content(
        val vacancyList: List<VacancyShort>,
    ) : SearchState()
}
