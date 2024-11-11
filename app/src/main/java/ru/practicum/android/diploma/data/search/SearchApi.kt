package ru.practicum.android.diploma.data.search

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("endpoint")
    suspend fun search(
        @Query("filters") text: String
    ): VacancyResponse
}
