package ru.practicum.android.diploma.data.search

import ru.practicum.android.diploma.util.EMPTY_STRING

data class VacancyDTO(
    val name: String,
    val hirer: String,
    val areas: String,
    val salaryFrom: Int = -1,
    val salaryTo: Int = -1,
    val currency: String = EMPTY_STRING,
    val artLink: String = EMPTY_STRING,
    val experience: String = EMPTY_STRING,
    val schedule: String = EMPTY_STRING,
    val description: String = EMPTY_STRING,
    val keySkills: String = EMPTY_STRING,
    val type: String,
    val vacancyId: String = EMPTY_STRING,
)
