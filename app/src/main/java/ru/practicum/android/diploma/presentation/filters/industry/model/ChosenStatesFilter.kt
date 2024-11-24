package ru.practicum.android.diploma.presentation.filters.industry.model

sealed interface ChosenStatesFilter {
    data object Chosen : ChosenStatesFilter
    data object NotChosen : ChosenStatesFilter
}
