package ru.practicum.android.diploma.di.favorite

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favorite.FavoriteViewModel

val favoriteViewModelModule = module {
    viewModel { FavoriteViewModel(get()) }
}
