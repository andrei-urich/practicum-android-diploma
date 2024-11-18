package ru.practicum.android.diploma.domain.favorite.interactors

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.search.models.VacancyShort

interface GetFavoriteVacanciesListInteractor {
    fun getFavVacanciesList(): Flow<List<VacancyShort>>
}
