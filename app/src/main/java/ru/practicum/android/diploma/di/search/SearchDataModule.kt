package ru.practicum.android.diploma.di.search

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.network.AppAPI
import ru.practicum.android.diploma.data.utils.InternetAccessChecker

val searchDataModule = module {
    single { InternetAccessChecker() }
    single<AppAPI> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppAPI::class.java)
    }
}
