package ru.practicum.android.diploma.data.filters.area

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.filters.area.dto.CountryDTO
import ru.practicum.android.diploma.data.filters.area.network.CountryListRequest
import ru.practicum.android.diploma.data.filters.area.network.CountryListResponse
import ru.practicum.android.diploma.data.search.network.NetworkClient
import ru.practicum.android.diploma.domain.filters.area.AreaFilterRepository
import ru.practicum.android.diploma.domain.filters.area.model.Country
import ru.practicum.android.diploma.domain.search.Resource

class AreaFilterRepositoryImpl(
    private val networkClient: NetworkClient,
) : AreaFilterRepository {
    override suspend fun getCountriesList(): Flow<Resource<List<Country>>> = flow {
        val request = CountryListRequest("RU")
        val response = networkClient.doRequest(request)
        when (response.resultCode) {
            in CODE_200..CODE_299 -> {
                if (response is CountryListResponse) {
                    val result: List<CountryDTO> = response.countries
                    val countries: List<Country> = result.map {
                        Country(
                            id = it.id,
                            name = it.name,
                        )
                    }
                    emit(Resource.Success(countries))
                } else {
                    emit(Resource.Error(response.resultCode))
                }
            }

            else -> emit(Resource.Error(response.resultCode))
        }
    }

    private companion object {
        const val CODE_299 = 299
        const val CODE_200 = 200
    }
}
