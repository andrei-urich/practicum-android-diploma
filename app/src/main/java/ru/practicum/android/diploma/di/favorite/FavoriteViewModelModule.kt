package ru.practicum.android.diploma.di.favorite

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favorite.FavoriteViewModel
import ru.practicum.android.diploma.util.DISPATCHER_IO_NAME

val favoriteViewModelModule = module {
    viewModel { FavoriteViewModel(get(),get(named(DISPATCHER_IO_NAME))) }
}
