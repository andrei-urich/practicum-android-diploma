package ru.practicum.android.diploma.presentation.filters.industry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.vacancydetails.ErrorType
import ru.practicum.android.diploma.data.vacancydetails.Success
import ru.practicum.android.diploma.domain.filters.industry.api.IndustryFilterInteractor
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterResult
import ru.practicum.android.diploma.presentation.filters.industry.model.ChosenStatesFilter
import ru.practicum.android.diploma.presentation.filters.industry.model.IndustryFilterStates

class IndustryFilterViewModel(private val interactor: IndustryFilterInteractor) : ViewModel() {
    private val _stateIndustry: MutableLiveData<IndustryFilterStates> = MutableLiveData()
    fun observeIndustryState(): LiveData<IndustryFilterStates> = _stateIndustry

    private var selectedIndustry: IndustryFilterModel? = null
    private var allIndustries: List<IndustryFilterModel> = listOf()

    private val _chosenStatesFilter: MutableLiveData<ChosenStatesFilter> = MutableLiveData()
    fun observeIndustryStateFilterChosen(): LiveData<ChosenStatesFilter> = _chosenStatesFilter

    fun getIndustry() {
        renderState(IndustryFilterStates.Loading)
        viewModelScope.launch {
            interactor.getIndustries().collect { pair: Pair<IndustryFilterResult?, ErrorType> ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun processResult(result: IndustryFilterResult?, errorType: ErrorType) {
        when (errorType) {
            is Success -> {
                if (result != null) {
                    allIndustries = result.industries
                    renderState(IndustryFilterStates.Content(result.industries))
                } else {
                    renderState(IndustryFilterStates.Empty)
                }
            }

            else -> {
                renderState(IndustryFilterStates.Error(errorType))
            }
        }
    }

    private fun renderState(state: IndustryFilterStates) {
        _stateIndustry.postValue(state)
    }

    private fun renderChosen(state: ChosenStatesFilter) {
        _chosenStatesFilter.postValue(state)
    }

    fun selectIndustry(industry: IndustryFilterModel) {
        if (selectedIndustry == industry) {
            selectedIndustry = null
            renderChosen(ChosenStatesFilter.NotChosen)
        } else {
            selectedIndustry = industry
            renderChosen(ChosenStatesFilter.Chosen)
        }
    }

    fun saveSelectedIndustry() {
        selectedIndustry?.let {
            interactor.saveIndustrySettings(it)
        }
    }

    fun getSelectedIndustry(): IndustryFilterModel? = interactor.getIndustrySettings()

    fun searchIndustries(query: String) {
        val filteredIndustries = if (query.isEmpty()) {
            allIndustries
        } else {
            allIndustries.filter { it.name.contains(query, ignoreCase = true) }
        }
        renderState(IndustryFilterStates.Content(filteredIndustries))
    }
}
