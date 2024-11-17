package ru.practicum.android.diploma.domain.vacancydetails.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.vacancydetails.EmployerInfo
import ru.practicum.android.diploma.data.vacancydetails.NameInfo
import ru.practicum.android.diploma.data.vacancydetails.ResourceDetails
import ru.practicum.android.diploma.data.vacancydetails.SalaryInfo
import ru.practicum.android.diploma.data.vacancydetails.network.AddressDto
import ru.practicum.android.diploma.data.vacancydetails.network.NameInfoDto
import ru.practicum.android.diploma.data.vacancydetails.network.NetworkClientDetails
import ru.practicum.android.diploma.data.vacancydetails.network.SalaryDto
import ru.practicum.android.diploma.data.vacancydetails.network.VacancyDetailsRequest
import ru.practicum.android.diploma.data.vacancydetails.network.VacancyDetailsResponse
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.vacancydetails.models.Address
import ru.practicum.android.diploma.domain.vacancydetails.models.Details
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails
import ru.practicum.android.diploma.util.EMPTY_STRING

class VacancyDetailsRepositoryImpl(private val networkClientDetails: NetworkClientDetails) : VacancyDetailsRepository {
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
                    transformSalaryInfo(it.salary)
                } else {
                    null
                },
                details = Details(
                    address = transformAddress(it.address),
                    experience = if (it.experience != null) {
                        transformExperience(it.experience)
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

    private fun transformSalaryInfo(salary: SalaryDto): SalaryInfo? {
        return SalaryInfo(
            salaryFrom = salary.from,
            salaryTo = salary.to,
            salaryCurrency = salary.currency
        )
    }

    private fun transformAddress(addressDto: AddressDto?): Address {
        return addressDto?.let {
            Address(
                city = it.city,
                building = it.building,
                street = it.street,
                description = it.description
            )
        } ?: Address(
            city = EMPTY_STRING,
            building = EMPTY_STRING,
            street = EMPTY_STRING,
            description = EMPTY_STRING
        )
    }

    private fun transformExperience(experience: NameInfoDto): NameInfo {
        return experience.let {
            NameInfo(
                id = it.id ?: EMPTY_STRING,
                name = it.name
            )
        }
    }
}
