package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.VacancyDetailsDto

data class VacancyDetailsResponse(
    val vacancy: VacancyDetailsDto,
) : Response()
