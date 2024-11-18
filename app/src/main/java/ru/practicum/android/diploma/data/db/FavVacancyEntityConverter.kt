package ru.practicum.android.diploma.data.db

import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

class FavVacancyEntityConverter {
    fun map(favVacancyEntity: FavVacancyEntity): VacancyShort {
        val vacancy = VacancyShort(
            name = favVacancyEntity.name,
            vacancyId = favVacancyEntity.vacancyId,
            employer = favVacancyEntity.hirer,
            area = favVacancyEntity.areas,
            salaryFrom = favVacancyEntity.salaryFrom,
            salaryTo = favVacancyEntity.salaryTo,
            currency = favVacancyEntity.currency,
            logoLink = favVacancyEntity.artLink
        )
        return vacancy
    }

    // 2DO Исправить конвертер после создания финальной версии Vacancy
    fun map(vacancy: VacancyDetails): FavVacancyEntity {
        return FavVacancyEntity(vacancy.name, vacancy.name, vacancy.name, type = vacancy.name)
    }
}
