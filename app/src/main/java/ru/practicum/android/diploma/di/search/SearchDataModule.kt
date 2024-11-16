package ru.practicum.android.diploma.di.search

import android.content.Context
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.network.AppAPI
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.search.SearchRepositoryImpl
import ru.practicum.android.diploma.data.utils.InternetAccessChecker
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.SearchInteractorImpl
import ru.practicum.android.diploma.domain.search.SearchRepository

val searchDataModule = module {
    single { InternetAccessChecker() }
    single<AppAPI> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppAPI::class.java)
    }

    factory { Gson() }

    factory<NetworkClient> {
        RetrofitNetworkClient(get(), get())
    }

    single<SearchRepository> {
        SearchRepositoryImpl(get())
    }

    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    single { (key: String) ->
        androidContext()
            .getSharedPreferences(key, Context.MODE_PRIVATE)
    }
}
