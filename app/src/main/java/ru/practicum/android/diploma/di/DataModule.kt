package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.util.InternetAccessChecker

val dataModule = module {
    single { InternetAccessChecker() }
}
