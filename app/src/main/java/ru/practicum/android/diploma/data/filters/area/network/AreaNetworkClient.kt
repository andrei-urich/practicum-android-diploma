package ru.practicum.android.diploma.data.filters.area.network

import ru.practicum.android.diploma.data.search.network.Response

interface AreaNetworkClient {
    suspend fun doRequest(dto: Any): Response
}
