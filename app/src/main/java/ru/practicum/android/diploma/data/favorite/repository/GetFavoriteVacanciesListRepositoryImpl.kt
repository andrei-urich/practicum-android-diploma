package ru.practicum.android.diploma.data.favorite.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.AjsAppDatabase
import ru.practicum.android.diploma.data.db.FavVacancyEntity
import ru.practicum.android.diploma.data.db.FavVacancyEntityConverter
import ru.practicum.android.diploma.domain.favorite.repository.GetFavoriteVacanciesListRepository
import ru.practicum.android.diploma.domain.search.models.VacancyShort

class GetFavoriteVacanciesListRepositoryImpl(
    private val database: AjsAppDatabase,
    private val favVacancyEntityConverter: FavVacancyEntityConverter,
) : GetFavoriteVacanciesListRepository {
    override fun getFavVacanciesList(): Flow<List<VacancyShort>> = flow {
        val favVacancyEntitiesList = database.favVacancyDao().getAllFavVacancies()
        emit(convertEntitiesToVacancies(favVacancyEntitiesList))
    }
    private fun convertEntitiesToVacancies(vacancyEntities: List<FavVacancyEntity>): List<VacancyShort> {
        return vacancyEntities.map { favVacancyEntity -> favVacancyEntityConverter.map(favVacancyEntity) }
    }
}
