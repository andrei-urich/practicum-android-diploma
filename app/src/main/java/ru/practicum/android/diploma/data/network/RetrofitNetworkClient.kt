package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.utils.InternetAccessChecker
import ru.practicum.android.diploma.util.ACCESS_TOKEN

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
                    resultCode = RESULT_CODE_BAD_REQUEST
                }
            }
        } else {
            resultCode = RESULT_CODE_OFFLINE
        }
        return Response().apply { resultCode }
    }

    companion object {
        const val RESULT_CODE_OK = 200
        const val RESULT_CODE_BAD_REQUEST = 400
        const val RESULT_CODE_OFFLINE = -1
    }
}
