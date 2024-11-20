package ru.practicum.android.diploma.presentation.vacancydetails

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.vacancydetails.ErrorType
import ru.practicum.android.diploma.data.vacancydetails.NoInternetError
import ru.practicum.android.diploma.data.vacancydetails.Success
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailFavoriteInteractor
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.vacancydetails.models.DetailsNotFoundType
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails
import ru.practicum.android.diploma.presentation.vacancydetails.model.VacancyDetailsState

class VacancyDetailsViewModel(
    private val vacancyInteractor: VacancyDetailsInteractor,
    private val vacancyDetailFavoriteInteractor: VacancyDetailFavoriteInteractor
) : ViewModel() {

//    private var isFavorite: Boolean = false

    private val vacancyState = MutableLiveData<VacancyDetailsState>()
    private val favoriteButtonState = MutableLiveData<Boolean>(false)
    fun observeVacancyState(): LiveData<VacancyDetailsState> = vacancyState
    fun getFavoriteButtonStateLD(): LiveData<Boolean> = favoriteButtonState
    var vacancy: VacancyDetails? = null

    fun getVacancy(vacancyId: String) {
        renderState(VacancyDetailsState.Loading)
        viewModelScope.launch {
            vacancyInteractor.getVacancyDetail(vacancyId).collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }
    fun checkInFavorite(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            vacancyDetailFavoriteInteractor.getVacancyFromFavorites(vacancyId).collect {
                if (it == null) {
                    favoriteButtonState.postValue(false)
                } else {
                    favoriteButtonState.postValue(true)
                }
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

    fun controlFavorites(vacancyId: String) {
        if (favoriteButtonState.value == true) {
            viewModelScope.launch {
                vacancyDetailFavoriteInteractor.deleteVacancyFromFavorites(vacancyId)
                favoriteButtonState.postValue(false)
            }
        } else {
            viewModelScope.launch {
                vacancyDetailFavoriteInteractor.addVacancyToFavorites(vacancy!!)
                favoriteButtonState.postValue(true)
            }
        }
    }

    fun getSharingIntent(): Intent? {
        return if (vacancy != null) {
            vacancyInteractor.shareVacancy(vacancy!!.details.hhVacancyLink)
        } else {
            null
        }
    }

}
