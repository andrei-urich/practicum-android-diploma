package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.utils.InternetAccessChecker

class RetrofitNetworkClient(
    private val apiService: SearchApi,
    private val internetAccessChecker: InternetAccessChecker
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (internetAccessChecker.isConnected()) {
            if (dto is VacancySearchRequest) {
                val response = withContext(Dispatchers.IO) { apiService.search(dto.request) }
                return response.apply { resultCode = 200 }
            } else {
                return Response().apply { resultCode = 400 }

            }
        } else {
            return Response().apply { resultCode = -1 }
        }
    }
}

