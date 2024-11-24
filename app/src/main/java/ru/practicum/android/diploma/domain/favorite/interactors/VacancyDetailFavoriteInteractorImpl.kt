package ru.practicum.android.diploma.domain.favorite.interactors

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.favorite.repository.VacancyDetailFavoriteRepository
import ru.practicum.android.diploma.domain.search.models.VacancyShort
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

    override suspend fun getVacancyFromFavorites(vacancyID: String): VacancyDetails? {
        return vacancyDetailFavoriteRepository.getVacancyFromFavorites(vacancyID)
    }

    override suspend fun getAllFavouritesVacanciesId(): Flow<List<String>> {
        return vacancyDetailFavoriteRepository.getAllFavouritesVacanciesId()
    }

    override fun getFavVacanciesList(): Flow<List<VacancyShort>> {
        return vacancyDetailFavoriteRepository.getFavVacanciesList()
    }
}
