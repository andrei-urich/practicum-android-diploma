package ru.practicum.android.diploma.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.favorite.interactors.GetFavoriteVacanciesListInteractor
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import java.io.IOException

class FavoriteViewModel(
    private val getFavoriteVacanciesListInteractor: GetFavoriteVacanciesListInteractor,
    private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {
    private val favoriteVacanciesScreenStateLiveData =
        MutableLiveData<FavoritesScreenState>(FavoritesScreenState.LoadingFavoriteScreen)

    fun getFavoriteVacanciesScreenStateLiveData(): LiveData<FavoritesScreenState> {
        return favoriteVacanciesScreenStateLiveData
    }

    fun getFavoriteVacanciesList() {
        viewModelScope.launch(dispatcherIO) {
            try {
                getFavoriteVacanciesListInteractor
                    .getFavVacanciesList()
                    .collect { vacancies ->
                        if (vacancies.isEmpty()) {
                            favoriteVacanciesScreenStateLiveData.postValue(FavoritesScreenState.EmptyFavoriteScreen)
                        } else {
                            val vacancyBases = vacancies.map { vacancy ->
                                VacancyShort(
                                    vacancyId = vacancy.vacancyId,
                                    name = vacancy.name,
                                    employer = vacancy.employer,
                                    area = vacancy.area,
                                    salaryTo = vacancy.salaryTo,
                                    salaryFrom = vacancy.salaryFrom,
                                    currency = vacancy.currency,
                                    logoLink = vacancy.logoLink,
                                )
                            }
                            val vacancyArrayList = ArrayList(vacancyBases)
                            favoriteVacanciesScreenStateLiveData.postValue(
                                FavoritesScreenState.FilledFavoriteScreen(
                                    vacancyArrayList
                                )
                            )
                        }
                    }
            } catch (e: IOException) {
                favoriteVacanciesScreenStateLiveData.postValue(FavoritesScreenState.Error(e))
            }
        }
    }
}
