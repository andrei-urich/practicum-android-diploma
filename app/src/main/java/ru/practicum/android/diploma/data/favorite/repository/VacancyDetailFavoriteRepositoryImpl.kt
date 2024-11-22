package ru.practicum.android.diploma.data.favorite.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.vacancydetails.db.DetailVacancyEntity
import ru.practicum.android.diploma.data.vacancydetails.db.DetailVacancyEntityConverter
import ru.practicum.android.diploma.data.vacancydetails.db.VacancyDatabase
import ru.practicum.android.diploma.domain.favorite.repository.VacancyDetailFavoriteRepository
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

class VacancyDetailFavoriteRepositoryImpl(
    private val vacancyDatabase: VacancyDatabase,
    private val detailVacancyEntityConverter: DetailVacancyEntityConverter
) : VacancyDetailFavoriteRepository {
    override suspend fun addVacancyToFavorites(vacancy: VacancyDetails) {
        vacancyDatabase.vacancyDao().addVacancyToFavourite(detailVacancyEntityConverter.map(vacancy))
    }

    override suspend fun deleteVacancyFromFavorites(vacancyID: String) {
        vacancyDatabase.vacancyDao().deleteVacancyFromFavourite(vacancyID)
    }

    override suspend fun getVacancyFromFavorites(vacancyID: String): VacancyDetails? {
        val vacancyEntity = vacancyDatabase.vacancyDao().getVacancyById(vacancyID) ?: return null
        return vacancyEntity.let { detailVacancyEntityConverter.mapDt(it) }
    }

    override suspend fun getAllFavouritesVacanciesId(): Flow<List<String>> = flow {
        emit(vacancyDatabase.vacancyDao().getAllFavouritesVacanciesId())
    }

    override fun getFavVacanciesList(): Flow<List<VacancyShort>> = flow {
        val vacancies = vacancyDatabase.vacancyDao().getAllFavouritesVacancies()
        emit(convertVacancyList(vacancies))

    }

    private fun convertVacancyList(vacanciesEntity: List<DetailVacancyEntity>): List<VacancyShort> {
        return vacanciesEntity.map { vacancyEntity -> detailVacancyEntityConverter.mapSt(vacancyEntity) }
    }

}
