package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.utils.InternetAccessChecker
import ru.practicum.android.diploma.util.ACCESS_TOKEN

class RetrofitNetworkClient(
    private val apiService: AppAPI, private val internetAccessChecker: InternetAccessChecker
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (internetAccessChecker.isConnected()) {
            when (dto) {
                is VacancySearchRequest -> {
                    val response = withContext(Dispatchers.IO) {
                        apiService.search(
                            token = ACCESS_TOKEN, dto.request
                        )
                    }
                    return response.apply { resultCode = 200 }
                }

                else -> {
                    return Response().apply { resultCode = 400 }
                }

            }
        } else {
            return Response().apply { resultCode = -1 }
        }
    }
}

