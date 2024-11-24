package ru.practicum.android.diploma.di.filters

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.filters.FilterSettingsViewModel
import ru.practicum.android.diploma.presentation.filters.area.AreaFilterViewModel
import ru.practicum.android.diploma.presentation.filters.area.CountryFilterViewModel
import ru.practicum.android.diploma.presentation.filters.area.RegionFilterViewModel

val filtersViewModelModule = module {
    viewModel { FilterSettingsViewModel() }
    viewModel { AreaFilterViewModel() }
    viewModel { CountryFilterViewModel() }
    viewModel { RegionFilterViewModel() }
}
