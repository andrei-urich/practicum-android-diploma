package ru.practicum.android.diploma.data.search.filters

data class FilterDTO(
    val areaId: String = EMPTY_STRING,
    val industryId: String = EMPTY_STRING,
    val salaryTarget: String = EMPTY_STRING,
    val showSalaryFlag: String = EMPTY_STRING
) {
    companion object {
        private const val EMPTY_STRING = ""
    }
}
