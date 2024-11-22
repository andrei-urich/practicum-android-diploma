package ru.practicum.android.diploma.data.search.dto

data class SalaryDTO(
    val currency: String? = EMPTY_STRING,
    val from: Int?,
    val to: Int?
)
private const val EMPTY_STRING = ""
