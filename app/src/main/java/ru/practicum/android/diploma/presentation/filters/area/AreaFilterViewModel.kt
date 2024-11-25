package ru.practicum.android.diploma.presentation.filters.area

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filters.area.AreaFilterInteractor

class AreaFilterViewModel (
    val interactor: AreaFilterInteractor
) : ViewModel() {
    private val stateLiveData = MutableLiveData<AreaState>()

    fun getStateLiveData(): LiveData<AreaState> = stateLiveData
}
