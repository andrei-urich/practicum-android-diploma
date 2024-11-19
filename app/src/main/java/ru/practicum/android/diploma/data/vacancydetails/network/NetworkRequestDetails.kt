package ru.practicum.android.diploma.data.vacancydetails.network

import ru.practicum.android.diploma.data.vacancydetails.ResponseBase

interface NetworkRequestDetails {
    suspend fun doRequest(dto: Any): ResponseBase
}
