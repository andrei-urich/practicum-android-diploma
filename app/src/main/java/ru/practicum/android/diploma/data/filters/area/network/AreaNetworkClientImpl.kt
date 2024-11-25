package ru.practicum.android.diploma.data.filters.area.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.search.network.Response
import java.io.IOException

class AreaNetworkClientImpl(
    private val apiService: AreaFilterApi
) : AreaNetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is CountryListRequest -> apiService.getCountries(dto.locale).apply { resultCode = CODE_200 }
                    else -> Response().apply { resultCode = RESULT_CODE_BAD_REQUEST }
                }
            } catch (e: retrofit2.HttpException) {
                println(e)
                Response().apply { resultCode = RESULT_CODE_SERVER_ERROR }
            } catch (e: IOException) {
                println(e)
                Response().apply { resultCode = RESULT_CODE_NO_INTERNET_ERROR }
            }
        }
    }

    private companion object {
        const val RESULT_CODE_BAD_REQUEST = 400
        const val RESULT_CODE_SERVER_ERROR = 500
        const val RESULT_CODE_NO_INTERNET_ERROR = 504
        const val CODE_200 = 200
    }
}
