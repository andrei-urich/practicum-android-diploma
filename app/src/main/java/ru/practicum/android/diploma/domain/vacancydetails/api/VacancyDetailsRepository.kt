package ru.practicum.android.diploma.domain.vacancydetails.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.vacancydetails.ResourceDetails
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

interface VacancyDetailsRepository {
    fun getVacancyDetails(vacancyId: String): Flow<ResourceDetails<VacancyDetails?>>
}
