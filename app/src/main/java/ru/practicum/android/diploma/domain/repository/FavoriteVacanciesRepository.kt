package ru.practicum.android.diploma.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoriteVacanciesRepository {
    suspend fun getFavVacanciesList(): Flow<List<Vacancy>>
    suspend fun checkVacancyInFavorites(vacancyId: Int): Boolean
    suspend fun addVacancyInFavorites(vacancy: Vacancy)
    suspend fun deleteVacancyFromFavorites(vacancyId: Int)
}
