package ru.practicum.android.diploma.di.search

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.search.SearchViewModel

val searchViewModelModule = module {
    viewModel {
        SearchViewModel(get(), get())
    }
}
