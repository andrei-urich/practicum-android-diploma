package ru.practicum.android.diploma.data.search.dto

import ru.practicum.android.diploma.util.EMPTY_STRING

data class EmployerDTO(
    val name: String,
    val logo: String? = EMPTY_STRING
)
