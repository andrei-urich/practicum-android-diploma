package ru.practicum.android.diploma.di.favorite

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.AjsAppDatabase
import ru.practicum.android.diploma.data.db.FavVacancyEntityConverter

val favoriteDataModule = module {
    single {
        Room.databaseBuilder(androidContext(), AjsAppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    factory { FavVacancyEntityConverter() }
}