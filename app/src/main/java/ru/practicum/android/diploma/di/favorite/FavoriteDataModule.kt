package ru.practicum.android.diploma.di.favorite

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.practicum.android.diploma.util.DISPATCHER_IO_NAME

val favoriteDataModule = module {

    single<CoroutineDispatcher>(named(DISPATCHER_IO_NAME)) { Dispatchers.IO }
}
