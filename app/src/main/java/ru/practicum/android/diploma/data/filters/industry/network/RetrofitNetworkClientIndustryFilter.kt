package ru.practicum.android.diploma.data.filters.industry.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.vacancydetails.BadRequestError
import ru.practicum.android.diploma.data.vacancydetails.NoInternetError
import ru.practicum.android.diploma.data.vacancydetails.ResponseBase
import ru.practicum.android.diploma.data.vacancydetails.ServerInternalError
import ru.practicum.android.diploma.data.vacancydetails.network.NetworkRequestDetails
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterErrorType
import ru.practicum.android.diploma.util.isConnected
import java.io.IOException
import java.net.HttpURLConnection

class RetrofitNetworkClientIndustryFilter(val api: AppApiIndustryFilter, val context: Context) :
    NetworkRequestDetails {
    override suspend fun doRequest(dto: Any): ResponseBase {
        if (!isConnected(context)) {
            return ResponseBase(NoInternetError())
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getIndustries()
                when (response.code()) {
                    HttpURLConnection.HTTP_OK -> convertIndustryResponse(response.body())
                    HttpURLConnection.HTTP_BAD_REQUEST -> ResponseBase(IndustryFilterErrorType())
                    else -> ResponseBase(BadRequestError())
                }
            } catch (e: HttpException) {
                e.message?.let { Log.e("Http", it) }
                ResponseBase(ServerInternalError())
            } catch (e: IOException) {
                e.message?.let { Log.e("IO", it) }
                ResponseBase(ServerInternalError())
            }
        }
    }

    private fun convertIndustryResponse(responseBody: IndustryFilterResponse?): ResponseBase =
        if (responseBody == null) {
            ResponseBase(IndustryFilterErrorType())
        } else {
            IndustryFilterResponse(
                responseBody.industries
            )
        }
}
