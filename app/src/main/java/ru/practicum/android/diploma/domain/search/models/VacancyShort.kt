package ru.practicum.android.diploma.domain.search.models

import ru.practicum.android.diploma.util.EMPTY_STRING

data class VacancyShort(
val name: String,
val employer: String,
val areas: String,
val salary: String,
val currency: String = EMPTY_STRING,
val artLink: String = EMPTY_STRING,
val vacancyId: String = EMPTY_STRING,
)
