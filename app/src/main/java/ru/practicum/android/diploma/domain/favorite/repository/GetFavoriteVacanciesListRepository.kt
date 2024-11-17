package ru.practicum.android.diploma.domain.favorite.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.search.models.VacancyShort

interface GetFavoriteVacanciesListRepository {
    fun getFavVacanciesList(): Flow<List<VacancyShort>>
}
