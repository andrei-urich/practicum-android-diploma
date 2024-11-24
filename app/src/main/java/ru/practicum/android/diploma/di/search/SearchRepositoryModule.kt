package ru.practicum.android.diploma.di.search

import org.koin.dsl.module
import ru.practicum.android.diploma.data.search.SearchRepositoryImpl
import ru.practicum.android.diploma.data.search.filters.FilterToFilterDTOConverter
import ru.practicum.android.diploma.domain.search.SearchRepository

val searchRepositoryModule = module {
    single<SearchRepository> {
        SearchRepositoryImpl(get(), get(), get(), get(), get())
    }
    single<FilterToFilterDTOConverter> { FilterToFilterDTOConverter() }
}
