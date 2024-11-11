package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.AjsAppDatabase
import ru.practicum.android.diploma.data.utils.InternetAccessChecker

val dataModule = module {
    single { InternetAccessChecker() }
    single {
        Room.databaseBuilder(androidContext(), AjsAppDatabase::class.java, "ajsdatabase.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}
