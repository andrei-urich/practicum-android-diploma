package ru.practicum.android.diploma.di.vacancydetails

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel

val vacancyDetailsViewModelModule = module {

    viewModel {
        VacancyDetailsViewModel(get())
    }
}
