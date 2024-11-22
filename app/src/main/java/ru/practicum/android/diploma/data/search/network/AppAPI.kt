package ru.practicum.android.diploma.data.search.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig

interface AppAPI {
    @Headers(
        "HH-User-Agent: $HH_REQUEST_HEADER",
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}"
    )
    @GET("/vacancies/")
    suspend fun search(
        @QueryMap options: Map<String, String>
    ): VacanciesResponse
    private companion object {
        const val HH_REQUEST_HEADER = "Almighty job seeker/1.0 (andrei.urich@yandex.ru)"
    }
}
