package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.search.models.VacancyShort

interface SearchInteractor {
    fun search(
        searchText: String,
        currentPage: Int
    ): Flow<Pair<List<VacancyShort>?, Int?>>

    fun checkNet(): Boolean
}
