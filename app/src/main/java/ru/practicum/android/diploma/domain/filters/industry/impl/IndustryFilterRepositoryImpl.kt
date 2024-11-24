package ru.practicum.android.diploma.domain.filters.industry.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.filters.industry.dto.IndustryFilterDTO
import ru.practicum.android.diploma.data.filters.industry.network.IndustryFilterRequest
import ru.practicum.android.diploma.data.filters.industry.network.IndustryFilterResponse
import ru.practicum.android.diploma.data.vacancydetails.ResourceDetails
import ru.practicum.android.diploma.data.vacancydetails.network.NetworkClientDetails
import ru.practicum.android.diploma.domain.filters.industry.api.IndustryFilterRepository
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterResult

class IndustryFilterRepositoryImpl(private val networkClient: NetworkClientDetails) : IndustryFilterRepository {
    override fun getIndustries(): Flow<ResourceDetails<IndustryFilterResult>> = flow {
        when (val response = networkClient.doRequest(IndustryFilterRequest())) {
            is IndustryFilterResponse -> {
                emit(
                    ResourceDetails
                        .Success(
                            IndustryFilterResult(
                                industries = response.industries.map(::convertIndustry)
                            )
                        )
                )

            }
            else -> {
                emit(ResourceDetails.Error(response.errorType))
            }
        }
    }

    private fun convertIndustry(it: IndustryFilterDTO): IndustryFilterModel =
        IndustryFilterModel(
            it.id,
            it.name,
        )
}
