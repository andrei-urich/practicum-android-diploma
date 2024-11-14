package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.vacancydetails.api.DetailsRepository
import ru.practicum.android.diploma.domain.vacancydetails.impl.DetailsRepositoryImpl

val repositoryModule = module {

    single<DetailsRepository> {
        DetailsRepositoryImpl(get())
    }
}
