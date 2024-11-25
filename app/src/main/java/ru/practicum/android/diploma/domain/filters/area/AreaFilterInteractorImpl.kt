package ru.practicum.android.diploma.domain.filters.area

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.filters.area.model.Region
import ru.practicum.android.diploma.domain.search.Resource
import ru.practicum.android.diploma.domain.search.models.VacancyShort

class AreaFilterInteractorImpl(
    val repository: AreaFilterRepository
) : AreaFilterInteractor {
    override suspend fun getCountriesList(): Flow<Pair<List<Region>?, Int?>> {
        return repository.getCountriesList().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.resultCode)
                }
            }
        }
    }

    override suspend fun getAllRegions(): Flow<Pair<List<Region>?, Int?>> {
        return repository.getAllRegions().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.resultCode)
                }
            }
        }
    }

    override suspend fun getInnerRegionsList(areaId: Int): Flow<Pair<List<Region>?, Int?>> {
        return repository.getInnerRegionsList(areaId).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.resultCode)
                }
            }
        }
    }
}
