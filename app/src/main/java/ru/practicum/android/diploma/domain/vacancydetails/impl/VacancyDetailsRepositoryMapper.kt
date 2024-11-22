package ru.practicum.android.diploma.domain.vacancydetails.impl

import ru.practicum.android.diploma.data.vacancydetails.NameInfo
import ru.practicum.android.diploma.data.vacancydetails.SalaryInfo
import ru.practicum.android.diploma.data.vacancydetails.network.AddressDto
import ru.practicum.android.diploma.data.vacancydetails.network.NameInfoDto
import ru.practicum.android.diploma.data.vacancydetails.network.SalaryDto
import ru.practicum.android.diploma.domain.vacancydetails.models.Address

class VacancyDetailsRepositoryMapper {
    fun transformSalaryInfo(salary: SalaryDto): SalaryInfo {
        return SalaryInfo(
            salaryFrom = salary.from,
            salaryTo = salary.to,
            salaryCurrency = salary.currency
        )
    }

    fun transformAddress(addressDto: AddressDto?): Address {
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

    fun transformExperience(experience: NameInfoDto): NameInfo {
        return experience.let {
            NameInfo(
                id = it.id ?: EMPTY_STRING,
                name = it.name
            )
        }
    }
    private companion object {
        const val EMPTY_STRING = ""
    }
}
