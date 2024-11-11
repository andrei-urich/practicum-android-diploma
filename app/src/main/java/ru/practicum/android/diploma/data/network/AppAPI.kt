package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.util.HH_REQUEST_HEADER

interface AppAPI {
    @GET("/vacancies")
    @Headers(HH_REQUEST_HEADER)
    suspend fun search(
        @Header("Authorization: Bearer") token: String,
        @Query("search input") text: String
    ): VacanciesResponse

    @GET("/vacancies/{vacancy_id}")
    @Headers(HH_REQUEST_HEADER)
    suspend fun getVacancy(
        @Header("Authorization: Bearer") token: String,
        @Path("vacancy_id") id: String
    ): VacanciesResponse
}
