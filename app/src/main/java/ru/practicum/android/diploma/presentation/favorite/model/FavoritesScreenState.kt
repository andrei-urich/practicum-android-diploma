package ru.practicum.android.diploma.presentation.favorite.model

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class FavoritesScreenState {
    data object EmptyFavoriteScreen : FavoritesScreenState()
    data object LoadingFavoriteScreen : FavoritesScreenState()
    data class FilledFavoriteScreen(val listOfFavoriteVacancies: List<Vacancy>) : FavoritesScreenState()
}
