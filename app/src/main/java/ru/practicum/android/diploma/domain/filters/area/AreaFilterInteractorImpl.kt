package ru.practicum.android.diploma.domain.filters.area

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filters.area.model.Country
import ru.practicum.android.diploma.domain.search.Resource

class AreaFilterInteractorImpl(
    val repository: AreaFilterRepository
) : AreaFilterInteractor {
    override suspend fun getCountriesList(): Flow<Resource<List<Country>>> {
        return repository.getCountriesList()
    }
}
