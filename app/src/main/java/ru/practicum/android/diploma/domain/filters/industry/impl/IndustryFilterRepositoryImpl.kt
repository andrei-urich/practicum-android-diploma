package ru.practicum.android.diploma.domain.filters.industry.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.filters.industry.IndustryFilterStorageRepository
import ru.practicum.android.diploma.data.filters.industry.dto.IndustryFilterDTO
import ru.practicum.android.diploma.data.filters.industry.network.IndustryFilterRequest
import ru.practicum.android.diploma.data.filters.industry.network.IndustryFilterResponse
import ru.practicum.android.diploma.data.vacancydetails.ResourceDetails
import ru.practicum.android.diploma.data.vacancydetails.network.NetworkRequestDetails
import ru.practicum.android.diploma.domain.filters.industry.api.IndustryFilterRepository
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel

class IndustryFilterRepositoryImpl(
    private val networkClient: NetworkRequestDetails,
    private val storage: IndustryFilterStorageRepository
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
            industryItems.add(IndustryFilterModel(subIndustry.id ?: EMPTY_STRING, subIndustry.name ?: EMPTY_STRING))
        }
        return industryItems
    }

    override fun saveIndustrySettings(industry: IndustryFilterModel) {
        storage.writeIndustry(industry)
    }

    override fun getIndustrySettings(): IndustryFilterModel? {
        return storage.readIndustry()
    }

    override fun deleteIndustrySettings() {
        storage.removeIndustry()
    }
    private companion object {
        const val EMPTY_STRING = ""
    }
}
