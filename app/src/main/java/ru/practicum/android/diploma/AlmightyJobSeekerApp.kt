package ru.practicum.android.diploma

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.favorite.favoriteDataModule
import ru.practicum.android.diploma.di.favorite.favoriteInteractorModule
import ru.practicum.android.diploma.di.favorite.favoriteRepositoryModule
import ru.practicum.android.diploma.di.favorite.favoriteViewModelModule
import ru.practicum.android.diploma.di.search.searchDataModule
import ru.practicum.android.diploma.di.vacancydetails.vacancyDetailsDataModule
import ru.practicum.android.diploma.di.vacancydetails.vacancyDetailsInteractorModule
import ru.practicum.android.diploma.di.vacancydetails.vacancyDetailsRepositoryModule
import ru.practicum.android.diploma.di.vacancydetails.vacancyDetailsViewModelModule

class AlmightyJobSeekerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AlmightyJobSeekerApp)
            modules(
                searchDataModule,
                favoriteDataModule,
                favoriteRepositoryModule,
                favoriteInteractorModule,
                favoriteViewModelModule,
                vacancyDetailsDataModule,
                vacancyDetailsRepositoryModule,
                vacancyDetailsInteractorModule,
                vacancyDetailsViewModelModule
            )
        }
    }
}
