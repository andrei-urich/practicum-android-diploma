package ru.practicum.android.diploma.data.search

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.search.filters.FilterDTO
import ru.practicum.android.diploma.data.search.filters.FilterToFilterDTOConverter
import ru.practicum.android.diploma.data.search.network.NetworkClient
import ru.practicum.android.diploma.data.search.network.VacanciesResponse
import ru.practicum.android.diploma.data.search.network.VacancySearchRequest
import ru.practicum.android.diploma.domain.filters.Filters
import ru.practicum.android.diploma.domain.search.Resource
import ru.practicum.android.diploma.domain.search.SearchRepository
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.util.isConnected

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val context: Context,
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
    private val filtersToFilterDTOConverter: FilterToFilterDTOConverter
) : SearchRepository {
    override fun search(
        searchText: String,
        currentPage: Int
    ): Flow<Resource<List<VacancyShort>>> = flow {
        val request: HashMap<String, String> = constructRequest(searchText, currentPage)
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
                            found = response.found,
                            pages = response.pages,
                            page = response.page
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

    override fun checkNet(): Boolean {
        return isConnected(context)
    }

    private var currentFiltersDTO = FilterDTO()
    private fun constructRequest(searchText: String, currentPage: Int): HashMap<String, String> {
        currentFiltersDTO = refreshFilters()
        val options: HashMap<String, String> = HashMap()
        options["text"] = searchText
        if (currentPage != 0) {
            options["page"] = currentPage.toString()
        }
        options["per_page"] = PER_PAGE.toString()
        if (currentFiltersDTO.areaId != EMPTY_STRING) options["area"] = currentFiltersDTO.areaId
        if (currentFiltersDTO.industryId != EMPTY_STRING) options["industry"] = currentFiltersDTO.industryId
        if (currentFiltersDTO.salaryTarget != EMPTY_STRING) options["salary"] = currentFiltersDTO.salaryTarget
        if (currentFiltersDTO.showSalaryFlag != "false") options["only_with_salary"] = currentFiltersDTO.showSalaryFlag
        return options
    }

    private fun refreshFilters(): FilterDTO {
        @Suppress("SwallowedException", "TooGenericExceptionCaught")
        try {
            return filtersToFilterDTOConverter.map(
                gson.fromJson(
                    sharedPreferences.getString(
                        FILTERS_ACTIVE, EMPTY_STRING
                    ) ?: EMPTY_STRING,
                    Filters::class.java
                )
            )
        } catch (e: Exception) {
            val filtersJson = gson.toJson(Filters())
            sharedPreferences.edit().putString(FILTERS_ACTIVE, filtersJson).apply()
            return filtersToFilterDTOConverter.map(Filters())
        }
    }

    private companion object {
        private const val EMPTY_STRING = ""
        private const val FILTERS_ACTIVE = "FILTERS_ACTIVE"
        const val CODE_299 = 299
        const val CODE_200 = 200
        const val PER_PAGE = 20
    }
}
