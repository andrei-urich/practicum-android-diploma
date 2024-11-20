package ru.practicum.android.diploma.domain.vacancydetails.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailFavoriteInteractor
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailFavoriteRepository
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

class VacancyDetailFavoriteInteractorImpl(
    private val vacancyDetailFavoriteRepository: VacancyDetailFavoriteRepository
) :
    VacancyDetailFavoriteInteractor {
    override suspend fun addVacancyToFavorites(vacancy: VacancyDetails) {
        vacancyDetailFavoriteRepository.addVacancyToFavorites(vacancy)
    }

    override suspend fun deleteVacancyFromFavorites(vacancyID: String) {
        vacancyDetailFavoriteRepository.deleteVacancyFromFavorites(vacancyID)
    }

    override suspend fun getVacancyFromFavorites(vacancyID: String): Flow<VacancyDetails?> {
        return vacancyDetailFavoriteRepository.getVacancyFromFavorites(vacancyID)
    }
}
