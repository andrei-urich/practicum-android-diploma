package ru.practicum.android.diploma.di.filters

import org.koin.dsl.module
import ru.practicum.android.diploma.data.filters.filters.FiltersControlRepositoryImpls
import ru.practicum.android.diploma.domain.filters.filters.repository.FiltersControlRepository

val filtersRepositoryModule = module {
    single<FiltersControlRepository> { FiltersControlRepositoryImpls(get(), get()) }
}
