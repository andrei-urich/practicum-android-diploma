package ru.practicum.android.diploma.domain.vacancydetails.api

import android.content.Intent

interface ExternalNavigator {
    fun shareVacancy(link: String): Intent
}
