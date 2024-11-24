package ru.practicum.android.diploma.di.filters

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.filters.industry.api.IndustryFilterInteractor
import ru.practicum.android.diploma.domain.filters.industry.impl.IndustryFilterInteractorImpl

val filtersInteractorModule = module {
    factory<IndustryFilterInteractor> {
        IndustryFilterInteractorImpl(get())
    }
}
