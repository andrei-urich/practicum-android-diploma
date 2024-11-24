package ru.practicum.android.diploma.domain.favorite.interactors

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

interface VacancyDetailFavoriteInteractor {
    suspend fun addVacancyToFavorites(vacancy: VacancyDetails)
    suspend fun deleteVacancyFromFavorites(vacancyID: String)
    suspend fun getVacancyFromFavorites(vacancyID: String): VacancyDetails?
    suspend fun getAllFavouritesVacanciesId(): Flow<List<String>>
    fun getFavVacanciesList(): Flow<List<VacancyShort>>
}