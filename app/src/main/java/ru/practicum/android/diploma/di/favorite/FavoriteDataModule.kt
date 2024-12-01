package ru.practicum.android.diploma.di.favorite

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val favoriteDataModule = module {

    single<CoroutineDispatcher>(named("dispatcherIO")) { Dispatchers.IO }
}
