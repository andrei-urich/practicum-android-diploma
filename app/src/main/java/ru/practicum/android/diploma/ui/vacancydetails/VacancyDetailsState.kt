package ru.practicum.android.diploma.ui.vacancydetails

import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

sealed interface VacancyDetailsState {
    data object Loading : VacancyDetailsState
    data object Empty : VacancyDetailsState
    data object NoInternet : VacancyDetailsState
    data class Content(val vacancy: VacancyDetails) : VacancyDetailsState
    // data class Error(val doRequest: RetrofitNetworkClient) : VacancyDetailsState <- нужен будет для обработки ошибок
}
