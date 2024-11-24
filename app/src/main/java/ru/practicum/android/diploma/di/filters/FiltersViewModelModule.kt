package ru.practicum.android.diploma.di.filters

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.filters.industry.IndustryFilterViewModel
import ru.practicum.android.diploma.presentation.filters.settings.FilterSettingsViewModel

val filtersViewModelModule = module {
    viewModel { FilterSettingsViewModel(get(), get()) }
    viewModel { IndustryFilterViewModel(get()) }
}
