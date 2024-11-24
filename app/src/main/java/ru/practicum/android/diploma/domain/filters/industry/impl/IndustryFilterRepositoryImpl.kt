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

class IndustryFilterRepositoryImpl(
    private val networkClient: NetworkClientDetails,
) : IndustryFilterRepository {
    override fun getIndustries(): Flow<ResourceDetails<List<IndustryFilterModel>>> = flow {
        when (val response = networkClient.doRequest(IndustryFilterRequest())) {
            is IndustryFilterResponse -> {
                val industryItems = response.industries.flatMap { convertIndustry(it) }
                emit(ResourceDetails.Success(industryItems.sortedBy { it.name }))
            }
            else -> {
                emit(ResourceDetails.Error(response.errorType))
            }
        }
    }

    override fun searchIndustries(query: String): Flow<ResourceDetails<List<IndustryFilterModel>>> = flow {
        when (val response = networkClient.doRequest(IndustryFilterRequest(query))) {
            is IndustryFilterResponse -> {
                val industryItems = response.industries.flatMap { convertIndustry(it) }
                emit(ResourceDetails.Success(industryItems.sortedBy { it.name }))
            }
            else -> {
                emit(ResourceDetails.Error(response.errorType))
            }
        }
    }

    private fun convertIndustry(it: IndustryFilterDTO): List<IndustryFilterModel> {
        val industryItems = mutableListOf<IndustryFilterModel>()
        industryItems.add(IndustryFilterModel(it.id, it.name))
        it.industries?.forEach { subIndustry ->
            industryItems.add(IndustryFilterModel(subIndustry.id ?: "", subIndustry.name ?: ""))
        }
        return industryItems
    }

    override fun saveIndustrySettings(industry: IndustryFilterModel) {
        TODO("сделать сейв")
    }

    override fun getIndustrySettings(): IndustryFilterModel? {
        TODO("сделать гет")
    }

    override fun deleteIndustrySettings() {
        TODO("сделать делит")
    }
}
