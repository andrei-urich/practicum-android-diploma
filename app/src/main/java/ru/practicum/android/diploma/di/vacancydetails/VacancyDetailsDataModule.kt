package ru.practicum.android.diploma.di.vacancydetails

import androidx.room.Room.databaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.db.AjsAppDatabase
import ru.practicum.android.diploma.data.vacancydetails.network.AppApiDetails

val vacancyDetailsDataModule = module {

    single<AppApiDetails> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppApiDetails::class.java)
    }

    single {
        databaseBuilder(androidContext(), AjsAppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}
