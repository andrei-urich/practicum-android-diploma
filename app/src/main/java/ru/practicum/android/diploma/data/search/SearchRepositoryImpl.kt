package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.VacanciesResponse
import ru.practicum.android.diploma.data.network.VacancySearchRequest
import ru.practicum.android.diploma.domain.search.Resource
import ru.practicum.android.diploma.domain.search.SearchRepository
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.util.CODE_200
import ru.practicum.android.diploma.util.CODE_299

class SearchRepositoryImpl(
    private val networkClient: NetworkClient
) : SearchRepository {
    override fun search(request: String): Flow<Resource<List<VacancyShort>>> = flow {
        val response = networkClient.doRequest(VacancySearchRequest(request))
        when (response.resultCode) {
            in CODE_200..CODE_299 -> {
                if (response is VacanciesResponse) {
                    val list = response.results
                    val vacancies = list.map {
                        VacancyShort(
                            it.id,
                            it.name,
                            it.employer,
                            it.area,
                            it.salary
                        )
                    }
                    emit(Resource.Success(vacancies))
                } else {
                    emit(Resource.Error(response.resultCode))
                }
            }

            else -> emit(Resource.Error(response.resultCode))
        }
    }
}

