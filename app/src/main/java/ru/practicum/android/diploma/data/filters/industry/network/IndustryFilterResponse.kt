package ru.practicum.android.diploma.data.filters.industry.network

import ru.practicum.android.diploma.data.filters.industry.dto.IndustryFilterDTO
import ru.practicum.android.diploma.data.vacancydetails.ResponseBase

data class IndustryFilterResponse(val industries: List<IndustryFilterDTO>) : ResponseBase()
