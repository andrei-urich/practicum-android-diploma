package ru.practicum.android.diploma.domain.favorite.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface GetFavoriteVacanciesListRepository {
    fun getFavVacanciesList(): Flow<List<Vacancy>>
}
