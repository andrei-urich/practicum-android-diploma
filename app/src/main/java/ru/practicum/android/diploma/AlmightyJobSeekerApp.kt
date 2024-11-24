package ru.practicum.android.diploma

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.favorite.favoriteDataModule
import ru.practicum.android.diploma.di.favorite.favoriteViewModelModule
import ru.practicum.android.diploma.di.filters.filtersDataModule
import ru.practicum.android.diploma.di.filters.filtersInteractorModule
import ru.practicum.android.diploma.di.filters.filtersRepositoryModule
import ru.practicum.android.diploma.di.filters.filtersViewModelModule
import ru.practicum.android.diploma.di.search.searchDataModule
import ru.practicum.android.diploma.di.search.searchViewModelModule
import ru.practicum.android.diploma.di.vacancydetails.vacancyDetailsDataModule
import ru.practicum.android.diploma.di.vacancydetails.vacancyDetailsInteractorModule
import ru.practicum.android.diploma.di.vacancydetails.vacancyDetailsRepositoryModule
import ru.practicum.android.diploma.di.vacancydetails.vacancyDetailsViewModelModule

const val FILTERS_ACTIVE = "FILTERS_ACTIVE"

class AlmightyJobSeekerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AlmightyJobSeekerApp)
            modules(
                searchDataModule,
                searchViewModelModule,
                vacancyDetailsDataModule,
                vacancyDetailsRepositoryModule,
                vacancyDetailsInteractorModule,
                vacancyDetailsViewModelModule,
                favoriteDataModule,
                favoriteViewModelModule,
                filtersDataModule,
                filtersInteractorModule,
                filtersRepositoryModule,
                filtersViewModelModule
            )
        }
    }
}
