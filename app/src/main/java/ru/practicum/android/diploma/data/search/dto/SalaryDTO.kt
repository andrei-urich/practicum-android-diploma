package ru.practicum.android.diploma.data.search.dto

import ru.practicum.android.diploma.util.EMPTY_STRING

data class SalaryDTO(
    val currency: String? = EMPTY_STRING,
    val from: Int?,
    val to: Int?
)
