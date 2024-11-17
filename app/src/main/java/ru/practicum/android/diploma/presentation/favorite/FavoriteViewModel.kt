package ru.practicum.android.diploma.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.favorite.interactors.GetFavoriteVacanciesListInteractor
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.util.CODE_200
import ru.practicum.android.diploma.util.CODE_299

class FavoriteViewModel(
    private val getFavoriteVacanciesListInteractor: GetFavoriteVacanciesListInteractor,
    private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {
    private val favoriteVacanciesScreenStateLiveData =
        MutableLiveData<FavoritesScreenState>(FavoritesScreenState.LoadingFavoriteScreen)

    fun getFavoriteVacanciesScreenStateLiveData(): LiveData<FavoritesScreenState> {
        return favoriteVacanciesScreenStateLiveData
    }

    fun getTestList() {
        favoriteVacanciesScreenStateLiveData.postValue(
            FavoritesScreenState.FilledFavoriteScreen(
                listOf(
                    VacancyShort("23", "developer", "yandex", "Spb", CODE_200, null, currency = "Р"),
                    VacancyShort("24", "developer2", "skillbox", "Msk", CODE_299, null, currency = "$")
                )
            )

        )
    }

    fun getFavoriteVacanciesList() {
        viewModelScope.launch(dispatcherIO) {
            getFavoriteVacanciesListInteractor.getFavVacanciesList().collect {
                if (it.isEmpty()) {
                    favoriteVacanciesScreenStateLiveData.postValue(FavoritesScreenState.EmptyFavoriteScreen)
                } else {
                    // favoriteVacanciesScreenStateLiveData.postValue(FavoritesScreenState.FilledFavoriteScreen(it))
                }
            }
        }
    }
}
