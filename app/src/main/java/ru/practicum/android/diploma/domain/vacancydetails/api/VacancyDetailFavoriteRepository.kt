package ru.practicum.android.diploma.domain.vacancydetails.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

interface VacancyDetailFavoriteRepository {
    suspend fun addVacancyToFavorites(vacancy: VacancyDetails)
    suspend fun deleteVacancyFromFavorites(vacancyID: String)
    fun getVacancyFromFavorites(vacancyID: String): Flow<VacancyDetails?>
}
