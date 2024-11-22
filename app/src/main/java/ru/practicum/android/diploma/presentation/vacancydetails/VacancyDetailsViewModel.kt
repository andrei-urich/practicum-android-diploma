package ru.practicum.android.diploma.presentation.vacancydetails

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.vacancydetails.ErrorType
import ru.practicum.android.diploma.data.vacancydetails.NoInternetError
import ru.practicum.android.diploma.data.vacancydetails.Success
import ru.practicum.android.diploma.domain.favorite.interactors.VacancyDetailFavoriteInteractor
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.vacancydetails.models.DetailsNotFoundType
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails
import ru.practicum.android.diploma.presentation.vacancydetails.model.VacancyDetailsState

class VacancyDetailsViewModel(
    private val vacancyInteractor: VacancyDetailsInteractor,
    private val vacancyDetailFavoriteInteractor: VacancyDetailFavoriteInteractor
) : ViewModel() {

    private var isFavorite: Boolean = false

    private val vacancyState = MutableLiveData<VacancyDetailsState>()
    fun observeVacancyState(): LiveData<VacancyDetailsState> = vacancyState
    private var favoriteVacanciesId: List<String>? = null
    var vacancy: VacancyDetails? = null

    fun getVacancy(vacancyId: String) {
        renderState(VacancyDetailsState.Loading)
        viewModelScope.launch {
            vacancyInteractor.getVacancyDetail(vacancyId).collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    fun addToFavById(vacancyId: VacancyDetails) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vacancyDetailFavoriteInteractor.addVacancyToFavorites(vacancyId)
            }
            isFavorite = true
            vacancyState.postValue(VacancyDetailsState.isFavorite(isFavorite))
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

    fun isFavorite(vacancyId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vacancyDetailFavoriteInteractor
                    .getAllFavouritesVacanciesId()
                    .collect {
                        favoriteVacanciesId = it
                    }
            }
            if (favoriteVacanciesId!!.contains(vacancyId)) {
                isFavorite = true
                vacancyState.postValue(VacancyDetailsState.isFavorite(isFavorite))
            } else {
                isFavorite = false
                vacancyState.postValue(VacancyDetailsState.isFavorite(isFavorite))
            }
        }
    }

    private fun renderState(state: VacancyDetailsState) {
        vacancyState.postValue(state)
    }

    fun deleteFavouriteVacancy(vacancyId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vacancyDetailFavoriteInteractor.deleteVacancyFromFavorites(vacancyId)
            }
            isFavorite = false
            vacancyState.postValue(VacancyDetailsState.isFavorite(isFavorite))
        }
    }

    fun getVacancyDatabase(vacancyId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val vacancy = vacancyDetailFavoriteInteractor.getVacancyFromFavorites(vacancyId)
                withContext(Dispatchers.Main) {
                    vacancy?.let {
                        vacancyState.postValue(VacancyDetailsState.Content(it))
                    } ?: run {
                        vacancyState.postValue(VacancyDetailsState.Empty)
                    }
                }
            }
        }
    }

    fun checkVacancyInDatabase(vacancyId: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val exists = withContext(Dispatchers.IO) {
                vacancyDetailFavoriteInteractor.getVacancyFromFavorites(vacancyId) != null
            }
            callback(exists)
        }
    }

    fun getFavouriteState(): Boolean {
        return isFavorite
    }

    fun getSharingIntent(): Intent? {
        return if (vacancy != null) {
            vacancyInteractor.shareVacancy(vacancy!!.details.hhVacancyLink)
        } else {
            null
        }
    }

}
