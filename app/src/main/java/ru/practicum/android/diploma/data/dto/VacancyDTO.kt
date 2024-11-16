package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.util.EMPTY_STRING

data class VacancyDTO(
    val vacancyId: String,
    val name: String,
    val employer: String,
    val areas: String,
    val salary: String,
    val currency: String = EMPTY_STRING,
    val artLink: String = EMPTY_STRING
)
