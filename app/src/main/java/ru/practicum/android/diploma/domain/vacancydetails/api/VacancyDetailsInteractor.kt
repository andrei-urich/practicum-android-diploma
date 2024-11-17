package ru.practicum.android.diploma.domain.vacancydetails.api

import android.content.Intent
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.vacancydetails.ErrorType
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

interface VacancyDetailsInteractor {
    fun getVacancyDetail(vacancyId: String): Flow<Pair<VacancyDetails?, ErrorType>>
    fun shareVacancy(hhVacancyLink: String): Intent
}
