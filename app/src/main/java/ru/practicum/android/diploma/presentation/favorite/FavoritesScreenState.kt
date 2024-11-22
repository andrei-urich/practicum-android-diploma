package ru.practicum.android.diploma.presentation.favorite

import ru.practicum.android.diploma.domain.search.models.VacancyShort
import java.io.IOException

sealed class FavoritesScreenState {
    data object EmptyFavoriteScreen : FavoritesScreenState()
    data object LoadingFavoriteScreen : FavoritesScreenState()
    data class FilledFavoriteScreen(val listOfFavoriteVacancies: ArrayList<VacancyShort>) : FavoritesScreenState()
    data class Error(val error: IOException) : FavoritesScreenState()
}
