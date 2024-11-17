package ru.practicum.android.diploma.domain.vacancydetails.api

import android.content.Intent

interface VacancyExternalNavigator {
    fun shareVacancy(link: String): Intent
}
