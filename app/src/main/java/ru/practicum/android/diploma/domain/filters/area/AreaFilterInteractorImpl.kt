package ru.practicum.android.diploma.domain.filters.area

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filters.area.model.Country
import ru.practicum.android.diploma.domain.filters.area.model.Region
import ru.practicum.android.diploma.domain.search.Resource

class AreaFilterInteractorImpl(
    val repository: AreaFilterRepository
) : AreaFilterInteractor {
    override suspend fun getCountriesList(): Flow<Resource<List<Country>>> {
        return repository.getCountriesList()
    }

    override suspend fun getAllRegions(): Flow<Resource<List<Region>>> {
        return repository.getAllRegions()
    }

    override suspend fun getInnerRegionsList(areaId: Int): Flow<Resource<List<Region>>> {
        return repository.getInnerRegionsList(areaId)
    }
}
