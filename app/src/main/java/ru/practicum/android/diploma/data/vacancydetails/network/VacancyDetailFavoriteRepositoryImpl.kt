package ru.practicum.android.diploma.data.vacancydetails.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.vacancydetails.db.DetailVacancyEntityConverter
import ru.practicum.android.diploma.data.vacancydetails.db.VacancyDatabase
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailFavoriteRepository
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

    override suspend fun getVacancyFromFavorites(vacancyID: String): Flow<VacancyDetails?> = flow {
        val vacancyEntity = vacancyDatabase.vacancyDao().getVacancyById(vacancyID)
        emit(detailVacancyEntityConverter.mapDt(vacancyEntity))
    }

}