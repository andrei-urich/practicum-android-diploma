package ru.practicum.android.diploma.presentation.favorite

import ru.practicum.android.diploma.domain.search.models.VacancyShort

sealed class FavoritesScreenState {
    data object EmptyFavoriteScreen : FavoritesScreenState()
    data object LoadingFavoriteScreen : FavoritesScreenState()
    data class FilledFavoriteScreen(val listOfFavoriteVacancies: List<VacancyShort>) : FavoritesScreenState()
}
