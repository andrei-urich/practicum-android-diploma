package ru.practicum.android.diploma.di.search

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.SearchInteractorImpl

val searchInteractorModule = module {
    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }
}
