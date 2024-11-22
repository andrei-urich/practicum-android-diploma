package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.search.models.VacancyShort

class SearchInteractorImpl(
    private val repository: SearchRepository
) : SearchInteractor {
    override fun search(request: HashMap<String, String>): Flow<Pair<List<VacancyShort>?, Int?>> {
        return repository.search(request).map { result ->
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

    override fun checkNet(): Boolean {
        return repository.checkNet()
    }
}
