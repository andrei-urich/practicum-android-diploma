package ru.practicum.android.diploma.domain.interactors

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.repository.FavoriteVacanciesRepository

class FavoriteVacanciesInteractor(private val favoriteVacanciesRepository: FavoriteVacanciesRepository) {
    suspend fun getFavVacanciesList(): Flow<List<Vacancy>> {
        return favoriteVacanciesRepository.getFavVacanciesList()
    }

    suspend fun checkVacancyInFavorites(vacancyId: Int): Boolean {
        return favoriteVacanciesRepository.checkVacancyInFavorites(vacancyId)
    }

    suspend fun addVacancyInFavorites(vacancy: Vacancy) {
        favoriteVacanciesRepository.addVacancyInFavorites(vacancy)
    }

    suspend fun deleteVacancyFromFavorites(vacancyId: Int) {
        favoriteVacanciesRepository.deleteVacancyFromFavorites(vacancyId)
    }
}
