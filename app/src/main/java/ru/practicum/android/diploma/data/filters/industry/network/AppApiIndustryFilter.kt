package ru.practicum.android.diploma.data.filters.industry.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.filters.industry.dto.IndustryFilterDTO

interface AppApiIndustryFilter {
    @Headers(
        "HH-User-Agent: $HH_REQUEST_HEADER",
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
    )
    @GET("/industries")
    suspend fun getIndustries(@Query("search") query: String? = null): Response<List<IndustryFilterDTO>>

    private companion object {
        const val HH_REQUEST_HEADER = "Almighty job seeker/1.0 (andrei.urich@yandex.ru)"
    }
}
