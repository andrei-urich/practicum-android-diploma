package ru.practicum.android.diploma.di.filters

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.FILTERS_ACTIVE

val filtersDataModule = module {
    single<SharedPreferences> { androidContext().getSharedPreferences(FILTERS_ACTIVE, Context.MODE_PRIVATE) }
}
