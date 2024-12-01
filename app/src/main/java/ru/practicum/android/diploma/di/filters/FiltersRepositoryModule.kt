package ru.practicum.android.diploma.di.filters

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.practicum.android.diploma.data.filters.area.AreaFilterRepositoryImpl
import ru.practicum.android.diploma.data.filters.filters.FiltersControlRepositoryImpls
import ru.practicum.android.diploma.data.filters.industry.IndustryFilterStorageRepository
import ru.practicum.android.diploma.data.filters.industry.IndustryFilterStorageRepositoryImpl
import ru.practicum.android.diploma.domain.filters.area.AreaFilterRepository
import ru.practicum.android.diploma.domain.filters.filters.repository.FiltersControlRepository
import ru.practicum.android.diploma.domain.filters.industry.api.IndustryFilterRepository
import ru.practicum.android.diploma.domain.filters.industry.impl.IndustryFilterRepositoryImpl

val filtersRepositoryModule = module {
    single<IndustryFilterRepository> {
        IndustryFilterRepositoryImpl(get(named("industry")), get())
    }
    single<FiltersControlRepository> { FiltersControlRepositoryImpls(get(), get()) }
    single<IndustryFilterStorageRepository> { IndustryFilterStorageRepositoryImpl(get(), get()) }
    single<AreaFilterRepository> {
        AreaFilterRepositoryImpl(get(), get())
    }
}
