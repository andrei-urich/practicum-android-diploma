package ru.practicum.android.diploma.data.vacancydetails

open class VacancyBase(
    val hhID: String,
    val name: String,
    var isFavorite: Boolean,
    val employerInfo: EmployerInfo,
    val salaryInfo: SalaryInfo?,
)
