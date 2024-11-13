package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.interactors.FavoriteVacanciesInteractor

val interactorModule = module {
    // DB
    single<FavoriteVacanciesInteractor> { FavoriteVacanciesInteractor(get()) }
}
