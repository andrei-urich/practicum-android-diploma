package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.search.models.VacancyShort

interface SearchRepository {
    fun search(request: String): Flow<Resource<List<VacancyShort>>>
}
