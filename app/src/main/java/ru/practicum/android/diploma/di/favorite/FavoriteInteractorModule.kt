package ru.practicum.android.diploma.di.favorite

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.favorite.interactors.GetFavoriteVacanciesListInteractor
import ru.practicum.android.diploma.domain.favorite.interactors.GetFavoriteVacanciesListInteractorImpl

val favoriteInteractorModule = module {
    single<GetFavoriteVacanciesListInteractor> { GetFavoriteVacanciesListInteractorImpl(get()) }
}
