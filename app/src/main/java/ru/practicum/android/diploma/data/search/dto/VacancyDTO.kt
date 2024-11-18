package ru.practicum.android.diploma.data.search.dto

data class VacancyDTO(
    val id: String,
    val name: String,
    val area: AreaDTO,
    val employer: EmployerDTO,
    val salary: SalaryDTO?,
    val found: Int,
    val page: Int,
    val pages: Int
)
