package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.utils.Currency
import ru.practicum.android.diploma.data.vacancydetails.SalaryInfo
import ru.practicum.android.diploma.domain.search.models.VacancyShort

object Formatter {
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
            string_ = "до " + vacancy.salaryTo.toString() + " " + currencyFromStr(vacancy.currency)
            string += " $string_"
        } else {
            string += " " + currencyFromStr(vacancy.currency)
        }
        return string
    }

    fun moneyFormat(num: Int) = "%,d".format(num).replace(",", " ")

    inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String): T? {
        return enumValues<T>().find { it.name == name }
    }

    private fun currencyFromStr(name: String?): String {
        return enumValueOfOrNull<Currency>(name.toString())?.abbr ?: name.toString()
    }

    fun formatSalary(context: Context, salaryInfo: SalaryInfo?): String {
        val salaryFrom = salaryInfo?.salaryFrom ?: 0
        val salaryTo = salaryInfo?.salaryTo ?: 0
        with(context) {
            val currency = currencyFromStr(salaryInfo?.salaryCurrency)
            return when {
                salaryFrom > 0 && salaryTo == salaryFrom -> getString(
                    R.string.salary_exact,
                    moneyFormat(salaryFrom),
                    currency
                )

                salaryFrom > 0 && salaryTo > 0 -> getString(
                    R.string.salary_from_to,
                    moneyFormat(salaryFrom),
                    moneyFormat(salaryTo),
                    currency
                )

                salaryFrom > 0 && salaryTo == 0 -> getString(
                    R.string.salary_from,
                    moneyFormat(salaryFrom),
                    currency
                )

                salaryFrom == 0 && salaryTo > 0 -> getString(
                    R.string.salary_to,
                    moneyFormat(salaryTo),
                    currency
                )

                else -> getString(R.string.salary_not_found)
            }
        }
    }
}
