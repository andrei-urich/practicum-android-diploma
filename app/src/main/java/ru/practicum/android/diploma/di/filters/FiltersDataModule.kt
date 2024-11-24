package ru.practicum.android.diploma.di.filters

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.FILTERS_ACTIVE
import ru.practicum.android.diploma.data.filters.industry.network.AppApiIndustryFilter

val filtersDataModule = module {
    single<AppApiIndustryFilter> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppApiIndustryFilter::class.java)
    }
    single<SharedPreferences> { androidContext().getSharedPreferences(FILTERS_ACTIVE, Context.MODE_PRIVATE) }
}
