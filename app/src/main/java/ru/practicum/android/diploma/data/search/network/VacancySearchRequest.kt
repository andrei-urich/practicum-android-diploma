package ru.practicum.android.diploma.data.search.network

data class VacancySearchRequest(
    val text: String,
    val page: String,
    val perPage: String
)
