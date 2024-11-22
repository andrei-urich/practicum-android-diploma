package ru.practicum.android.diploma.di.vacancydetails

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.practicum.android.diploma.data.favorite.repository.VacancyDetailFavoriteRepositoryImpl
import ru.practicum.android.diploma.data.vacancydetails.ExternalNavigatorImpl
import ru.practicum.android.diploma.domain.favorite.repository.VacancyDetailFavoriteRepository
import ru.practicum.android.diploma.domain.vacancydetails.api.ExternalNavigator
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.vacancydetails.impl.VacancyDetailsRepositoryImpl

val vacancyDetailsRepositoryModule = module {

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get(named("details")))
    }

    single<ExternalNavigator> {
        ExternalNavigatorImpl()
    }
    single<VacancyDetailFavoriteRepository> { VacancyDetailFavoriteRepositoryImpl(get(), get()) }
}
