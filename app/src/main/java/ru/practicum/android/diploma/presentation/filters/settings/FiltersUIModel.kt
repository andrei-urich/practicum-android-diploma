package ru.practicum.android.diploma.presentation.filters.settings

data class FiltersUIModel(
    val areaNCity: String = EMPTY_STRING,
    val industry: String = EMPTY_STRING,
    val salaryTarget: String = EMPTY_STRING,
    val salaryShowChecked: Boolean = false
) {
    companion object {
        private const val EMPTY_STRING = ""
    }
}
