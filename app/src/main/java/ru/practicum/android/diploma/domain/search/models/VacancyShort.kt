package ru.practicum.android.diploma.domain.search.models

import ru.practicum.android.diploma.util.EMPTY_STRING

data class VacancyShort(
    val vacancyId: String?,
    val name: String?,
    val employer: String? = EMPTY_STRING,
    val area: String? = EMPTY_STRING,
    val salaryTo: Int?,
    val salaryFrom: Int?,
    val currency: String? = EMPTY_STRING,
    val logoLink: String?,

    // Данные из конкретного поиска "по слову". Не нужно их в избранное.
    val found: Int,
    val page: Int,
    val pages: Int,

    // Оставил пока, чтобы ошибки не было в избранном (во вьюХолдере). Убрать
    val salary: String? = EMPTY_STRING
)
