package ru.practicum.android.diploma.presentation.filters.area

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AreaFilterViewModel : ViewModel() {
    private val stateLiveData = MutableLiveData<AreaState>()

    fun getStateLiveData(): LiveData<AreaState> = stateLiveData
}
