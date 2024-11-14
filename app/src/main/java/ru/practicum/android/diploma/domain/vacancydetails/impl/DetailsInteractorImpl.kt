package ru.practicum.android.diploma.domain.vacancydetails.impl

import com.bumptech.glide.load.engine.Resource
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacancydetails.api.DetailsInteractor
import ru.practicum.android.diploma.domain.vacancydetails.api.DetailsRepository
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

class DetailsInteractorImpl(
    private val repository: DetailsRepository,
) : DetailsInteractor {
    override fun getVacancyDetail(vacancyId: String): Flow<Resource<VacancyDetails?>> {
        return repository.getVacancyDetails(vacancyId)
    }
}
