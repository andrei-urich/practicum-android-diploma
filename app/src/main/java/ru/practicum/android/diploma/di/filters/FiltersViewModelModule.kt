package ru.practicum.android.diploma.di.filters

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.filters.area.AreaFilterViewModel
import ru.practicum.android.diploma.presentation.filters.area.CountryFilterViewModel
import ru.practicum.android.diploma.presentation.filters.area.RegionFilterViewModel
import ru.practicum.android.diploma.presentation.filters.industry.IndustryFilterViewModel
import ru.practicum.android.diploma.presentation.filters.settings.FilterSettingsViewModel

val filtersViewModelModule = module {
    viewModel { FilterSettingsViewModel(get(), get(), get(), get()) }
    viewModel { IndustryFilterViewModel(get()) }
    viewModel { AreaFilterViewModel(get()) }
    viewModel { CountryFilterViewModel(get(), get()) }
    viewModel { RegionFilterViewModel(get(), get()) }
}
