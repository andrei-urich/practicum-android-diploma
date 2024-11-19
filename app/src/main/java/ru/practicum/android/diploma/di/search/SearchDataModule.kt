package ru.practicum.android.diploma.di.search

import android.content.Context
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.RetrofitNetworkClient
import ru.practicum.android.diploma.data.search.network.AppAPI
import ru.practicum.android.diploma.data.search.network.NetworkClient

val searchDataModule = module {
    single<AppAPI> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppAPI::class.java)
    }

    factory { Gson() }

    factory<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
    }

    single { (key: String) ->
        androidContext()
            .getSharedPreferences(key, Context.MODE_PRIVATE)
    }
}
