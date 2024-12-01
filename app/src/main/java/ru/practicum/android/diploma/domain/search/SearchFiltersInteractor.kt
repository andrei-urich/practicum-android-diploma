package ru.practicum.android.diploma.domain.search

interface SearchFiltersInteractor {
    fun isFiltersNotEmpty(): Boolean
    fun isSearchForced(): Boolean
}
