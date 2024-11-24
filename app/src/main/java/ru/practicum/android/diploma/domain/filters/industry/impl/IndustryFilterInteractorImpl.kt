package ru.practicum.android.diploma.domain.filters.industry.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.vacancydetails.ErrorType
import ru.practicum.android.diploma.data.vacancydetails.ResourceDetails
import ru.practicum.android.diploma.domain.filters.industry.api.IndustryFilterInteractor
import ru.practicum.android.diploma.domain.filters.industry.api.IndustryFilterRepository
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterResult

class IndustryFilterInteractorImpl(private val repository: IndustryFilterRepository) : IndustryFilterInteractor {
    override fun getIndustries(): Flow<Pair<IndustryFilterResult?, ErrorType>> {
        return repository.getIndustries().map { result ->
            when (result) {
                is ResourceDetails.Success -> {
                    Pair(result.data, result.error)
                }
                is ResourceDetails.Error -> {
                    Pair(null, result.error)
                }
            }
        }
    }
}
