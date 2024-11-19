package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.search.models.VacancyShort

object SalaryToString {
    fun getSalary(
        vacancy: VacancyShort
    ): String {
        if (vacancy.salaryFrom == null && vacancy.salaryTo == null) {
            return "Зарплата не указана"
        }
        var string = ""
        val string_: String
        if (vacancy.salaryFrom != null) string = "от " + vacancy.salaryFrom.toString()
        if (vacancy.salaryTo != null) {
            string_ = "до " + vacancy.salaryTo.toString() + " " + vacancy.currency
            string += " " + string_
        } else {
            string += " " + vacancy.currency
        }
        return string
    }
}
