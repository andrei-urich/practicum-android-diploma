package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.utils.InternetAccessChecker
import ru.practicum.android.diploma.util.ACCESS_TOKEN
import ru.practicum.android.diploma.util.RESULT_CODE_ERROR
import ru.practicum.android.diploma.util.RESULT_CODE_NOT_FOUND
import ru.practicum.android.diploma.util.RESULT_CODE_OK

class RetrofitNetworkClient(
    private val apiService: AppAPI,
    private val internetAccessChecker: InternetAccessChecker
) : NetworkClient {
    private var resultCode: Int = 0

    override suspend fun doRequest(dto: Any): Response {
        if (internetAccessChecker.isConnected()) {
            when (dto) {
                is VacancySearchRequest -> {
                    val response = withContext(Dispatchers.IO) {
                        apiService.search(
                            token = ACCESS_TOKEN,
                            dto.request
                        )
                    }
                    return response.apply { resultCode = RESULT_CODE_OK }
                }

                else -> {
                    resultCode = RESULT_CODE_NOT_FOUND
                }
            }
        } else {
            resultCode = RESULT_CODE_ERROR
        }
        return Response().apply { resultCode }
    }
}
