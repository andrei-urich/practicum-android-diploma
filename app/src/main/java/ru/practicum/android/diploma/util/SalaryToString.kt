package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.search.models.VacancyShort

object SalaryToString {
    fun getSalary(
        vacancy: VacancyShort
    ): String {
        var string = ""
        val string_: String
        if (vacancy.salaryFrom != 0) string = "от " + vacancy.salaryFrom.toString()
        if (vacancy.salaryTo != 0) {
            string_ = "до " + vacancy.salaryTo.toString() + " " + vacancy.currency
            string += " " + string_
        } else {
            string += " " + vacancy.currency
        }
        return string
    }
}

