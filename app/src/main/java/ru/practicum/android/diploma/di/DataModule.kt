package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.db.AjsAppDatabase
import ru.practicum.android.diploma.data.network.AppAPI
import ru.practicum.android.diploma.data.utils.InternetAccessChecker

val dataModule = module {
    single { InternetAccessChecker() }
    single {
        Room.databaseBuilder(androidContext(), AjsAppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single<AppAPI> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppAPI::class.java)
    }
}
