package ru.practicum.android.diploma.presentation.filters.area

sealed class AreaState {
    object Country : AreaState()
    object Region : AreaState()
}
