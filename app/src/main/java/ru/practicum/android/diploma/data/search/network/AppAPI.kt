package ru.practicum.android.diploma.data.search.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.util.HH_REQUEST_HEADER

interface AppAPI {
    @Headers(
        "HH-User-Agent: ${HH_REQUEST_HEADER}",
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}"
    )
    @GET("/vacancies/")
    suspend fun search(
        @QueryMap options: Map<String, String>
    ): VacanciesResponse
}
