package ru.practicum.android.diploma.di.filters

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.filters.industry.network.AppApiIndustryFilter

val FiltersDataModule = module {
    single<AppApiIndustryFilter> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppApiIndustryFilter::class.java)
    }
}
