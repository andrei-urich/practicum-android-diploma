package ru.practicum.android.diploma.data.vacancydetails.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.util.HH_REQUEST_HEADER

interface AppApiDetails {
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: $HH_REQUEST_HEADER",
    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(@Path("vacancy_id") id: String): Response<VacancyDetailsResponse>
}
