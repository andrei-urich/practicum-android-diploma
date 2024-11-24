package ru.practicum.android.diploma.presentation.filters.area

import androidx.lifecycle.ViewModel

class CountryFilterViewModel : ViewModel() {
    fun setArea(s: String) {
        println(s)
    }

    fun getAreaList() {
        println()
    }
}
