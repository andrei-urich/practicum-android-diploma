package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.vacancydetails.api.DetailsInteractor
import ru.practicum.android.diploma.domain.vacancydetails.impl.DetailsInteractorImpl

val interactorModule = module {

    single<DetailsInteractor> {
        DetailsInteractorImpl(get())
    }
}
