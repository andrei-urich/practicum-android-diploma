package ru.practicum.android.diploma.data.vacancydetails.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.vacancydetails.BadRequestError
import ru.practicum.android.diploma.data.vacancydetails.NoInternetError
import ru.practicum.android.diploma.data.vacancydetails.ResponseBase
import ru.practicum.android.diploma.data.vacancydetails.ServerInternalError
import ru.practicum.android.diploma.domain.vacancydetails.models.DetailsNotFoundType
import ru.practicum.android.diploma.util.isConnected
import java.io.IOException
import java.net.HttpURLConnection

class NetworkClientDetails(
    private val hhApiServiceDetails: AppApiDetails,
    private val context: Context,
) : NetworkRequestDetails {
    override suspend fun doRequest(dto: Any): ResponseBase {
        if (!isConnected(context)) {
            return ResponseBase(NoInternetError())
        }
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is VacancyDetailsRequest -> {
                        val response = hhApiServiceDetails.getVacancyDetails(dto.vacancyID)
                        when (response.code()) {
                            HttpURLConnection.HTTP_OK -> convertVacancyDetailsResponse(response.body())
                            HttpURLConnection.HTTP_NOT_FOUND -> ResponseBase(DetailsNotFoundType())
                            else -> ResponseBase(BadRequestError())
                        }
                    }

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

    private fun convertVacancyDetailsResponse(responseBody: VacancyDetailsResponse?): ResponseBase =
        if (responseBody == null) {
            ResponseBase(DetailsNotFoundType())
        } else {
            VacancyDetailsResponse(
                responseBody.id,
                responseBody.name,
                responseBody.area,
                responseBody.address,
                responseBody.employer,
                responseBody.salary,
                responseBody.experience,
                responseBody.employment,
                responseBody.schedule,
                responseBody.description,
                responseBody.keySkills,
                responseBody.hhVacancyLink
            )
        }
}
