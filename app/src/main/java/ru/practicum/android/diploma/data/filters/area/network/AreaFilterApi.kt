package ru.practicum.android.diploma.data.filters.area.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.filters.area.dto.CountryDTO
import ru.practicum.android.diploma.data.filters.area.dto.RegionDTO

interface AreaFilterApi {
    @Headers(
        "HH-User-Agent: $HH_REQUEST_HEADER",
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
    )
    @GET("/areas/countries")
    suspend fun getCountries(@Query("locale") query: String?): Response<ArrayList<CountryDTO>>

    @GET("/areas")
    suspend fun getAllRegions(@Query("locale") query: String?): Response<ArrayList<RegionDTO>>

    @GET("/areas/{area_id}")
    suspend fun getInnerRegions(
        @Query("locale") query: String?,
        @Path("area_id") id: Int,
    ): Response<ArrayList<RegionDTO>>

    private companion object {
        const val HH_REQUEST_HEADER = "Almighty job seeker/1.0 (andrei.urich@yandex.ru)"
    }
}
