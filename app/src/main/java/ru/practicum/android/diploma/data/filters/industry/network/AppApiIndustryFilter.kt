package ru.practicum.android.diploma.data.filters.industry.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface AppApiIndustryFilter {
    @Headers(
        HH_REQUEST_HEADER
    )
    @GET("/industries")
    suspend fun getIndustries(): Response<IndustryFilterResponse>

    private companion object {
        const val HH_REQUEST_HEADER = "Almighty job seeker/1.0 (andrei.urich@yandex.ru)"
    }
}
