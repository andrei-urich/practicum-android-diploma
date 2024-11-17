package ru.practicum.android.diploma.di.search

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.search.SearchRepositoryImpl
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.SearchInteractorImpl
import ru.practicum.android.diploma.domain.search.SearchRepository
import ru.practicum.android.diploma.presentation.search.SearchViewModel

val searchViewModelModule = module {
    viewModel {
        SearchViewModel(get())
    }
    single<SearchRepository> {
        SearchRepositoryImpl(get())
    }

    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }
}
