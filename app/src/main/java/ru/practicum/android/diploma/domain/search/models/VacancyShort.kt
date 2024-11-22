package ru.practicum.android.diploma.domain.search.models

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
    val found: Int = ZERO,
    val page: Int = ZERO,
    val pages: Int = ZERO
)
private const val EMPTY_STRING = ""
private const val ZERO = 0
