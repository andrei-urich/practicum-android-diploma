package ru.practicum.android.diploma.domain.vacancydetails.impl

import android.content.Intent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.vacancydetails.ErrorType
import ru.practicum.android.diploma.data.vacancydetails.ResourceDetails
import ru.practicum.android.diploma.domain.vacancydetails.api.ExternalNavigator
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

class VacancyDetailsInteractorImpl(
    private val repository: VacancyDetailsRepository,
    private val externalNavigator: ExternalNavigator
) : VacancyDetailsInteractor {
    override fun getVacancyDetail(vacancyId: String): Flow<Pair<VacancyDetails?, ErrorType>> {
        return repository.getVacancyDetails(vacancyId).map { result ->
            when (result) {
                is ResourceDetails.Success -> {
                    Pair(result.data, result.error)
                }

                is ResourceDetails.Error -> {
                    Pair(null, result.error)
                }
            }
        }
    }

    override fun shareVacancy(hhVacancyLink: String): Intent {
        return externalNavigator.shareVacancy(hhVacancyLink)
    }
}
