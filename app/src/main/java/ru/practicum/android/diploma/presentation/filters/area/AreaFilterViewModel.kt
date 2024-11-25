package ru.practicum.android.diploma.presentation.filters.area

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filters.area.AreaFilterInteractor

class AreaFilterViewModel(
    val interactor: AreaFilterInteractor
) : ViewModel() {
    fun getCountryList() {
        println()
    }
}
