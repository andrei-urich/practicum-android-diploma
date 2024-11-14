package ru.practicum.android.diploma.domain.vacancydetails.api

import com.bumptech.glide.load.engine.Resource
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

interface DetailsInteractor {
    fun getVacancyDetail(vacancyId: String): Flow<Resource<VacancyDetails?>>
}
