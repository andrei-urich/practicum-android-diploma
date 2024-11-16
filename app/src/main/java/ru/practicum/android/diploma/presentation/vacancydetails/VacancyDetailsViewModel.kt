package ru.practicum.android.diploma.presentation.vacancydetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.vacancydetails.api.DetailsInteractor
import ru.practicum.android.diploma.ui.vacancydetails.VacancyDetailsState

class VacancyDetailsViewModel(
    private val vacancyDetailsInteractor: DetailsInteractor,
) : ViewModel() {
    private val vacancyDetailsState = MutableLiveData<VacancyDetailsState>()

    fun getDetailsVacancy(vacancyId: String) {
        renderState(VacancyDetailsState.Loading)
        viewModelScope.launch {
            vacancyDetailsInteractor.getVacancyDetail(vacancyId)
        }
    }

    private fun renderState(state: VacancyDetailsState) {
        vacancyDetailsState.postValue(state)
    }
}