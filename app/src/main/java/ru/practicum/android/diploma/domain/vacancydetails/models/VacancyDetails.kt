package ru.practicum.android.diploma.domain.vacancydetails.models

import ru.practicum.android.diploma.data.vacancydetails.EmployerInfo
import ru.practicum.android.diploma.data.vacancydetails.NameInfo
import ru.practicum.android.diploma.data.vacancydetails.SalaryInfo
import ru.practicum.android.diploma.data.vacancydetails.VacancyBase

class VacancyDetails(
    hhID: String,
    name: String,
    isFavorite: Boolean,
    employerInfo: EmployerInfo,
    salaryInfo: SalaryInfo?,
    val details: Details,
) : VacancyBase(
    hhID,
    name,
    isFavorite,
    employerInfo,
    salaryInfo
)

data class Details(
    val address: Address?,
    val experience: NameInfo?,
    val employment: NameInfo?,
    val schedule: NameInfo?,
    val description: String,
    val keySkills: List<NameInfo>,
    val hhVacancyLink: String,
)

data class Address(
    val city: String?,
    val building: String?,
    val street: String?,
    val description: String?,
)
