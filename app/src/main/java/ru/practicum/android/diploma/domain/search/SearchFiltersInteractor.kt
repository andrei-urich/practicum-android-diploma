package ru.practicum.android.diploma.domain.search

import ru.practicum.android.diploma.domain.filters.Filters

interface SearchFiltersInteractor {
    fun isFiltersNotEmpty(): Boolean
    fun isSearchForced(): Boolean
}
