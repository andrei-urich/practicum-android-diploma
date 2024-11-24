package ru.practicum.android.diploma.presentation.filters.industry.model

import ru.practicum.android.diploma.data.vacancydetails.ErrorType
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel

sealed interface IndustryFilterStates {
    data object Loading : IndustryFilterStates
    data object Empty : IndustryFilterStates
    data class Content(val industries: List<IndustryFilterModel>) :
        IndustryFilterStates

    data class Error(val errorType: ErrorType) : IndustryFilterStates
}
