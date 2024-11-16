package ru.practicum.android.diploma.di.favorite

import androidx.room.Room
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.AjsAppDatabase
import ru.practicum.android.diploma.data.db.FavVacancyEntityConverter
import ru.practicum.android.diploma.util.DISPATCHER_IO_NAME

val favoriteDataModule = module {
    single {
        Room.databaseBuilder(androidContext(), AjsAppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    factory { FavVacancyEntityConverter() }
    single<CoroutineDispatcher>(named(DISPATCHER_IO_NAME)) { Dispatchers.IO }
}
