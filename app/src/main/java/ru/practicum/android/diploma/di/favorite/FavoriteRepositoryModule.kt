package ru.practicum.android.diploma.di.favorite

import org.koin.dsl.module
import ru.practicum.android.diploma.data.favorite.repository.GetFavoriteVacanciesListRepositoryImpl
import ru.practicum.android.diploma.domain.favorite.repository.GetFavoriteVacanciesListRepository

val favoriteRepositoryModule = module {
    single<GetFavoriteVacanciesListRepository> { GetFavoriteVacanciesListRepositoryImpl(get(), get()) }
}
