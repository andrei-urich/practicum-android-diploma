package ru.practicum.android.diploma.data.filters.area.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.practicum.android.diploma.BuildConfig

interface AreaFilterApi {
    @Headers(
        "HH-User-Agent: $HH_REQUEST_HEADER",
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
    )
    @GET("/areas/countries")
    suspend fun getCountries(@Query("locale") query: String?): CountryListResponse

    private companion object {
        const val HH_REQUEST_HEADER = "Almighty job seeker/1.0 (andrei.urich@yandex.ru)"
    }
}
