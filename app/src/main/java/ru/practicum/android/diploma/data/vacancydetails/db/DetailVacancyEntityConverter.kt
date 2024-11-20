package ru.practicum.android.diploma.data.vacancydetails.db

import ru.practicum.android.diploma.data.vacancydetails.EmployerInfo
import ru.practicum.android.diploma.data.vacancydetails.NameInfo
import ru.practicum.android.diploma.data.vacancydetails.SalaryInfo
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.domain.vacancydetails.models.Address
import ru.practicum.android.diploma.domain.vacancydetails.models.Details
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

class DetailVacancyEntityConverter {
    fun mapSt(detailVacancyEntity: DetailVacancyEntity): VacancyShort {
        return VacancyShort(
            name = detailVacancyEntity.name,
            vacancyId = detailVacancyEntity.hhID,
            employer = detailVacancyEntity.employerName,
            area = detailVacancyEntity.addressCity,
            salaryFrom = detailVacancyEntity.salaryFrom,
            salaryTo = detailVacancyEntity.salaryTo,
            currency = detailVacancyEntity.salaryCurrency,
            logoLink = detailVacancyEntity.employerLogoUrl
        )
    }

    fun mapDt(detailVacancyEntity: DetailVacancyEntity?): VacancyDetails? {
        if (detailVacancyEntity != null) {
            val address = Address(
                detailVacancyEntity.addressCity,
                detailVacancyEntity.addressBuilding,
                detailVacancyEntity.addressStreet,
                detailVacancyEntity.addressDescription
            )
            val vacancy = VacancyDetails(
                hhID = detailVacancyEntity.hhID,
                name = detailVacancyEntity.name,
                isFavorite = detailVacancyEntity.isFavorite, // zochem
                employerInfo = EmployerInfo(
                    detailVacancyEntity.employerName,
                    detailVacancyEntity.employerLogoUrl,
                    detailVacancyEntity.areaName
                ),
                salaryInfo = SalaryInfo(
                    detailVacancyEntity.salaryFrom,
                    detailVacancyEntity.salaryTo,
                    detailVacancyEntity.salaryCurrency
                ),
                details = Details(
                    address,
                    NameInfo(detailVacancyEntity.experienceId, detailVacancyEntity.experienceName.toString()),
                    NameInfo(detailVacancyEntity.employmentId, detailVacancyEntity.employerName),
                    NameInfo(detailVacancyEntity.scheduleId, detailVacancyEntity.scheduleName.toString()),
                    description = detailVacancyEntity.description,
                    keySkills = listOf(NameInfo(detailVacancyEntity.keySkills, detailVacancyEntity.keySkills)),
                    // ОБРАТИТЬ ВНИМАНИЕ, KEYSKILLS ИЗ СТРОКИ ПРЕВАРЩАЕТСЯ В НЕЧТО И ОБРАТНО
                    hhVacancyLink = detailVacancyEntity.hhVacancyLink
                )
            )
            return vacancy
        } else {
            return null
        }
    }

    fun map(vacancy: VacancyDetails): DetailVacancyEntity {
        val vacancyEntity = DetailVacancyEntity(
            hhID = vacancy.hhID,
            name = vacancy.name,
            isFavorite = vacancy.isFavorite,
            areaName = vacancy.employerInfo.areaName,
            employerName = vacancy.employerInfo.employerName,
            employerLogoUrl = vacancy.employerInfo.employerLogoUrl,
            salaryTo = vacancy.salaryInfo?.salaryTo,
            salaryFrom = vacancy.salaryInfo?.salaryFrom,
            salaryCurrency = vacancy.salaryInfo?.salaryCurrency,
            addressCity = vacancy.details.address?.city,
            addressBuilding = vacancy.details.address?.building,
            addressStreet = vacancy.details.address?.street,
            addressDescription = vacancy.details.address?.description,
            experienceId = vacancy.details.experience?.id,
            experienceName = vacancy.details.experience?.name,
            employmentId = vacancy.details.employment?.id,
            employmentName = vacancy.details.employment?.name,
            scheduleId = vacancy.details.schedule?.id,
            scheduleName = vacancy.details.schedule?.name,
            description = vacancy.details.description,
            keySkills = vacancy.details.keySkills.toString(),
            hhVacancyLink = vacancy.details.hhVacancyLink
        )
        return vacancyEntity
    }
}
