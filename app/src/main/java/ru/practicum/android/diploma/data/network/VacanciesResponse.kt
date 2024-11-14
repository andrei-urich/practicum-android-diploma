package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.VacancyDTO

data class VacanciesResponse(val results: ArrayList<VacancyDTO>) : Response()
