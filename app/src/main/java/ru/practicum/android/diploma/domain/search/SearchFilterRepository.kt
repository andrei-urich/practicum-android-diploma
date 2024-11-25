package ru.practicum.android.diploma.domain.search

interface SearchFilterRepository {
    fun isFiltersNotEmpty(): Boolean
    fun isSearchForced(): Boolean
    fun forceSearch()
}
