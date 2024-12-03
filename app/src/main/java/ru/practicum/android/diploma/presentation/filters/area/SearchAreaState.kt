package ru.practicum.android.diploma.presentation.filters.area

import ru.practicum.android.diploma.domain.filters.area.model.Region

sealed class SearchAreaState {
    data object Prepared : SearchAreaState()
    data object Loading : SearchAreaState()
    data class Error(
        val resultCode: Int?
    ) : SearchAreaState()

    data class Content(
        val areaList: List<Region>,
    ) : SearchAreaState()
}
