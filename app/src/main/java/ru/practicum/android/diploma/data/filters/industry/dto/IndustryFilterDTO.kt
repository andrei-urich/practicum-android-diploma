package ru.practicum.android.diploma.data.filters.industry.dto

data class IndustryFilterDTO(
    val id: String,
    val name: String,
    val industries: List<SubIndustries>?
)
