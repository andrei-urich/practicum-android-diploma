package ru.practicum.android.diploma.domain.vacancydetails.impl

import com.bumptech.glide.load.engine.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.VacancyDetailsRequest
import ru.practicum.android.diploma.domain.vacancydetails.api.DetailsRepository
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails

class DetailsRepositoryImpl(private val networkClient: NetworkClient) : DetailsRepository {
    override fun getVacancyDetails(vacancyId: String): Flow<Resource<VacancyDetails?>> = flow {
        networkClient.doRequest(VacancyDetailsRequest(vacancyId))
    }
}
