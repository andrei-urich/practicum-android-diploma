package ru.practicum.android.diploma.presentation.vacancydetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.vacancydetails.ErrorType
import ru.practicum.android.diploma.data.vacancydetails.NoInternetError
import ru.practicum.android.diploma.data.vacancydetails.Success
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.vacancydetails.models.DetailsNotFoundType
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails
import ru.practicum.android.diploma.presentation.vacancydetails.model.VacancyDetailsState

class VacancyDetailsViewModel(
    private val vacancyInteractor: VacancyDetailsInteractor,
) : ViewModel() {

//    private var isFavourite: Boolean = false

    private val vacancyState = MutableLiveData<VacancyDetailsState>()
    fun observeVacancyState(): LiveData<VacancyDetailsState> = vacancyState
    var vacancy: VacancyDetails? = null
//    private var favouriteTracksId: List<String>? = null

    fun getVacancy(vacancyId: String) {
        renderState(VacancyDetailsState.Loading)
        viewModelScope.launch {
            vacancyInteractor.getVacancyDetail(vacancyId).collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun processResult(vacancyDetails: VacancyDetails?, errorType: ErrorType) {
        when (errorType) {
            is Success -> {
                if (vacancyDetails != null) {
                    vacancy = vacancyDetails
                    renderState(
                        VacancyDetailsState.Content(vacancyDetails)
                    )
                } else {
                    renderState(
                        VacancyDetailsState.Empty
                    )
                }
            }

            is DetailsNotFoundType -> {
                renderState(
                    VacancyDetailsState.Empty
                )
            }

            is NoInternetError -> {
                renderState(
                    VacancyDetailsState.NoInternet
                )
            }

            else -> {
                renderState(
                    VacancyDetailsState.Error(errorType)
                )
            }
        }
    }

    private fun renderState(state: VacancyDetailsState) {
        vacancyState.postValue(state)
    }

}
