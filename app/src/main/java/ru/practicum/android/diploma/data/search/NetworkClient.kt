package ru.practicum.android.diploma.data.search

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
