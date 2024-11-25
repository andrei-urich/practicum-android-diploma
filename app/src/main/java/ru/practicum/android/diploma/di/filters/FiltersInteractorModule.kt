package ru.practicum.android.diploma.di.filters

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.filters.area.AreaFilterInteractor
import ru.practicum.android.diploma.domain.filters.area.AreaFilterInteractorImpl
import ru.practicum.android.diploma.domain.filters.filters.api.ControlFiltersInteractor
import ru.practicum.android.diploma.domain.filters.filters.api.ControlFiltersInteractorImpl
import ru.practicum.android.diploma.domain.filters.industry.api.IndustryFilterInteractor
import ru.practicum.android.diploma.domain.filters.industry.impl.IndustryFilterInteractorImpl
import ru.practicum.android.diploma.domain.search.SearchFiltersInteractor

val filtersInteractorModule = module {
    factory<IndustryFilterInteractor> {
        IndustryFilterInteractorImpl(get())
    }
    single<ControlFiltersInteractor> { ControlFiltersInteractorImpl(get(), get()) }
    single<SearchFiltersInteractor> { ControlFiltersInteractorImpl(get(), get()) }
    factory<AreaFilterInteractor> {
        AreaFilterInteractorImpl(get())
    }
}
