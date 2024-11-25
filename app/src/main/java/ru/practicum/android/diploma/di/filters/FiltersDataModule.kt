package ru.practicum.android.diploma.di.filters

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.FILTERS_ACTIVE
import ru.practicum.android.diploma.data.filters.area.network.AreaFilterApi
import ru.practicum.android.diploma.data.filters.area.network.AreaNetworkClient
import ru.practicum.android.diploma.data.filters.area.network.AreaNetworkClientImpl
import ru.practicum.android.diploma.data.filters.industry.network.AppApiIndustryFilter
import ru.practicum.android.diploma.data.filters.industry.network.RetrofitNetworkClientIndustryFilter
import ru.practicum.android.diploma.data.vacancydetails.network.NetworkRequestDetails
import ru.practicum.android.diploma.presentation.filters.settings.FiltersToFiltersUIMapper

val filtersDataModule = module {
    single<AppApiIndustryFilter> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppApiIndustryFilter::class.java)
    }
    single<SharedPreferences> { androidContext().getSharedPreferences(FILTERS_ACTIVE, Context.MODE_PRIVATE) }

    single<NetworkRequestDetails>(named("industry")) {
        RetrofitNetworkClientIndustryFilter(get(), androidContext())
    }
    single<FiltersToFiltersUIMapper> { FiltersToFiltersUIMapper() }

    single<AreaFilterApi> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AreaFilterApi::class.java)
    }
    factory<AreaNetworkClient> {
        AreaNetworkClientImpl(get())
    }
}
