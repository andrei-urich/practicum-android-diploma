package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.AjsAppDatabase
import ru.practicum.android.diploma.data.db.FavVacancyEntity
import ru.practicum.android.diploma.data.db.FavVacancyEntityConverter
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.repository.FavoriteVacanciesRepository

class FavoriteVacanciesRepositoryImpl(
    private val database: AjsAppDatabase,
    private val favVacancyEntityConverter: FavVacancyEntityConverter,
) : FavoriteVacanciesRepository {
    override suspend fun getFavVacanciesList(): Flow<List<Vacancy>> = flow {
        val favVacancyEntitiesList = database.favVacancyDao().getAllFavVacancies()
        emit(convertEntitiesToVacancies(favVacancyEntitiesList))
    }

    override suspend fun checkVacancyInFavorites(vacancyId: Int): Boolean {
        return database.favVacancyDao().getFavoriteVacancy(vacancyId) != null
    }

    override suspend fun addVacancyInFavorites(vacancy: Vacancy) {
        database.favVacancyDao().insertFavVacancy(convertVacancyToEntityOne(vacancy))
    }

    override suspend fun deleteVacancyFromFavorites(vacancyId: Int) {
        database.favVacancyDao().deleteFavVacancyFromDB(vacancyId)
    }

    private fun convertVacancyToEntityOne(vacancy: Vacancy): FavVacancyEntity {
        return favVacancyEntityConverter.map(vacancy)
    }

    private fun convertEntitiesToVacancies(vacancyEntities: List<FavVacancyEntity>): List<Vacancy> {
        return vacancyEntities.map { favVacancyEntity -> favVacancyEntityConverter.map(favVacancyEntity) }
    }
}
