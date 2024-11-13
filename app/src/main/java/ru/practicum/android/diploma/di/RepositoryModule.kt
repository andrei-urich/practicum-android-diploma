package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.repository.FavoriteVacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.repository.FavoriteVacanciesRepository

val repositoryModule = module {
    // DB
    single<FavoriteVacanciesRepository> { FavoriteVacanciesRepositoryImpl(get(), get()) }
}
