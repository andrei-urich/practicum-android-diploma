package ru.practicum.android.diploma.presentation.vacancydetails.model

import ru.practicum.android.diploma.data.vacancydetails.ErrorType
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

sealed interface VacancyDetailsState {
    data object Loading : VacancyDetailsState
    data object Empty : VacancyDetailsState
    data object NoInternet : VacancyDetailsState
    data class Error(val errorType: ErrorType) : VacancyDetailsState
    data class Content(val vacancy: VacancyDetails) : VacancyDetailsState
    data class isFavorite(val isFav: Boolean) : VacancyDetailsState
}
