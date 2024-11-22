package ru.practicum.android.diploma.domain.favorite.interactors

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.favorite.repository.GetFavoriteVacanciesListRepository
import ru.practicum.android.diploma.domain.search.models.VacancyShort

class GetFavoriteVacanciesListInteractorImpl(
    private val getFavoriteVacanciesListRepository: GetFavoriteVacanciesListRepository
) : GetFavoriteVacanciesListInteractor {
    override fun getFavVacanciesList(): Flow<List<VacancyShort>> {
        return getFavoriteVacanciesListRepository.getFavVacanciesList()
    }
}
