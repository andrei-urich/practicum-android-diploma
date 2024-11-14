package ru.practicum.android.diploma.domain.vacancydetails.api

import com.bumptech.glide.load.engine.Resource
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

interface DetailsRepository {
    fun getVacancyDetails(vacancyId: String): Flow<Resource<VacancyDetails?>>
}
