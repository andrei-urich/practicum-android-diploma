package ru.practicum.android.diploma.di.favorite

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.favorite.interactors.GetFavoriteVacanciesInteractorListImpl
import ru.practicum.android.diploma.domain.favorite.interactors.GetFavoriteVacanciesListInteractor

val favoriteInteractorModule = module {
    single<GetFavoriteVacanciesListInteractor> { GetFavoriteVacanciesInteractorListImpl(get()) }
}
