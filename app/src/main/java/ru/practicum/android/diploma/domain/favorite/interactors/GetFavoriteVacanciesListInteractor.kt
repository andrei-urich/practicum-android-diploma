package ru.practicum.android.diploma.domain.favorite.interactors

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface GetFavoriteVacanciesListInteractor {
    fun getFavVacanciesList(): Flow<List<Vacancy>>
}
