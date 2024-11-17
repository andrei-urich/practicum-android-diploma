package ru.practicum.android.diploma.data.vacancydetails.dto

data class VacancyDetailsDto(
    val id: Int,
    val name: String,
    val employerName: String,
    val employerLogoUrls: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?,
    val salaryGross: Boolean?,
    val details: Details,
)

data class Details(
    val address: Address?,
    val experience: String?,
    val employment: String?,
    val schedule: String?,
    val description: String,
    val keySkills: List<String>,
    val hhVacancyLink: String,
)

data class Address(
    val city: String?,
    val building: String?,
    val street: String?,
    val description: String?,
)
