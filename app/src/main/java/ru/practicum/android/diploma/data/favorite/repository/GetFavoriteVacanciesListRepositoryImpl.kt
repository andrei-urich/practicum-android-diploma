package ru.practicum.android.diploma.data.favorite.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.vacancydetails.db.DetailVacancyEntity
import ru.practicum.android.diploma.data.vacancydetails.db.DetailVacancyEntityConverter
import ru.practicum.android.diploma.data.vacancydetails.db.VacancyDatabase
import ru.practicum.android.diploma.domain.favorite.repository.GetFavoriteVacanciesListRepository
import ru.practicum.android.diploma.domain.search.models.VacancyShort

class GetFavoriteVacanciesListRepositoryImpl(
    private val database: VacancyDatabase,
    private val favVacancyEntityConverter: DetailVacancyEntityConverter,
) : GetFavoriteVacanciesListRepository {
    override fun getFavVacanciesList(): Flow<List<VacancyShort>> = flow {
        val vacancies = database.vacancyDao().getAllFavouritesVacancies()
        emit(convertVacancyList(vacancies))

    }

    private fun convertVacancyList(vacanciesEntity: List<DetailVacancyEntity>): List<VacancyShort> {
        return vacanciesEntity.map { vacancyEntity -> favVacancyEntityConverter.mapSt(vacancyEntity) }
    }

}
