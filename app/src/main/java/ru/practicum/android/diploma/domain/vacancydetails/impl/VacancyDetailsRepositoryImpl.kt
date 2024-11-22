package ru.practicum.android.diploma.domain.vacancydetails.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.vacancydetails.EmployerInfo
import ru.practicum.android.diploma.data.vacancydetails.NameInfo
import ru.practicum.android.diploma.data.vacancydetails.ResourceDetails
import ru.practicum.android.diploma.data.vacancydetails.network.NetworkRequestDetails
import ru.practicum.android.diploma.data.vacancydetails.network.VacancyDetailsRequest
import ru.practicum.android.diploma.data.vacancydetails.network.VacancyDetailsResponse
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.vacancydetails.models.Details
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

class VacancyDetailsRepositoryImpl(private val networkClientDetails: NetworkRequestDetails) : VacancyDetailsRepository {
    private var vacancyDetailsRepositoryMapper: VacancyDetailsRepositoryMapper? = null
    override fun getVacancyDetails(vacancyId: String): Flow<ResourceDetails<VacancyDetails?>> = flow {
        when (val response = networkClientDetails.doRequest(VacancyDetailsRequest(vacancyId))) {
            is VacancyDetailsResponse -> {
                emit(transformVacancyDetails(response))
            }

            else -> {
                emit(ResourceDetails.Error(response.errorType))
            }
        }
    }

    private fun transformVacancyDetails(it: VacancyDetailsResponse): ResourceDetails<VacancyDetails?> {
        return ResourceDetails.Success(
            VacancyDetails(
                it.id,
                it.name,
                isFavorite = false,
                employerInfo = EmployerInfo(
                    areaName = it.area.name,
                    employerName = it.employer?.name ?: EMPTY_STRING,
                    employerLogoUrl = it.employer?.logoUrls?.logo240
                ),
                salaryInfo = if (it.salary != null) {
                    vacancyDetailsRepositoryMapper?.transformSalaryInfo(it.salary)
                } else {
                    null
                },
                details = Details(
                    address = vacancyDetailsRepositoryMapper?.transformAddress(it.address),
                    experience = if (it.experience != null) {
                        vacancyDetailsRepositoryMapper?.transformExperience(it.experience)
                    } else {
                        null
                    },
                    employment = NameInfo(
                        id = it.employment?.id ?: EMPTY_STRING,
                        name = it.employment?.name ?: EMPTY_STRING
                    ),
                    schedule = NameInfo(id = it.schedule?.id ?: EMPTY_STRING, name = it.schedule?.name ?: EMPTY_STRING),
                    description = it.description,
                    keySkills = it.keySkills.map {
                        NameInfo(null, it.name)
                    },
                    hhVacancyLink = it.hhVacancyLink
                )
            )
        )
    }
    private companion object {
        const val EMPTY_STRING = ""
    }
}
