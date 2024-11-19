package ru.practicum.android.diploma.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.search.network.AppAPI
import ru.practicum.android.diploma.data.search.network.NetworkClient
import ru.practicum.android.diploma.data.search.network.Response
import ru.practicum.android.diploma.data.search.network.VacancySearchRequest
import ru.practicum.android.diploma.util.CODE_200
import ru.practicum.android.diploma.util.RESULT_CODE_BAD_REQUEST
import ru.practicum.android.diploma.util.RESULT_CODE_ERROR
import ru.practicum.android.diploma.util.isConnected

class RetrofitNetworkClient(
    private val apiService: AppAPI,
    private val context: Context
) : NetworkClient {
    private var resultCode: Int = 0

    override suspend fun doRequest(dto: Any): Response {
        if (isConnected(context)) {
            when (dto) {
                is VacancySearchRequest -> {
                    val response = withContext(Dispatchers.IO) {
                        apiService.search(dto.request)
                    }
                    return response.apply { resultCode = CODE_200 }
                }

                else -> {
                    resultCode = RESULT_CODE_BAD_REQUEST
                }
            }
        } else {
            resultCode = RESULT_CODE_ERROR
        }
        return Response().apply { resultCode }
    }
}
