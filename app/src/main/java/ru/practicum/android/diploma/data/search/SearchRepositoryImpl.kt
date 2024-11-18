package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.search.network.NetworkClient
import ru.practicum.android.diploma.data.search.network.VacanciesResponse
import ru.practicum.android.diploma.data.search.network.VacancySearchRequest
import ru.practicum.android.diploma.domain.search.Resource
import ru.practicum.android.diploma.domain.search.SearchRepository
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.util.CODE_200
import ru.practicum.android.diploma.util.CODE_299
import ru.practicum.android.diploma.util.EMPTY_STRING

class SearchRepositoryImpl(
    private val networkClient: NetworkClient
) : SearchRepository {
    override fun search(
        request: HashMap<String, String>
    ): Flow<Resource<List<VacancyShort>>> = flow {
        val response = networkClient.doRequest(VacancySearchRequest(request))
        when (response.resultCode) {
            in CODE_200..CODE_299 -> {
                if (response is VacanciesResponse) {
                    val result = response.items
                    val vacancies: List<VacancyShort> = result.map {
                        VacancyShort(
                            vacancyId = it.id,
                            name = it.name,
                            employer = it.employer.name,
                            area = it.area.name,
                            salaryFrom = it.salary?.from,
                            salaryTo = it.salary?.to,
                            currency = it.salary?.currency,
                            logoLink = it.employer.logoUrls?.original ?: EMPTY_STRING,
                            found = it.found,
                            pages = it.pages,
                            page = it.page
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
