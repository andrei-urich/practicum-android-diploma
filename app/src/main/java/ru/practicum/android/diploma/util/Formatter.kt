package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.utils.Currency
import ru.practicum.android.diploma.data.vacancydetails.SalaryInfo
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import java.util.Locale

object Formatter {
    fun getSalary(
        vacancy: VacancyShort,
    ): String {
        changeLanguage()
        if (vacancy.salaryFrom == null && vacancy.salaryTo == null) {
            return SALARY_NOT_SPECIFIED
        }
        var string = EMPTY_STRING
        val string_: String
        if (vacancy.salaryFrom != null) string = FROM + moneyFormat(vacancy.salaryFrom)
        if (vacancy.salaryFrom != null && vacancy.salaryTo == null) string = FROM + moneyFormat(vacancy.salaryFrom)
        if (vacancy.salaryTo != null) {
            string_ = TO + moneyFormat(vacancy.salaryTo) + EMPTY_STRING_WITH_SPACE + currencyFromStr(vacancy.currency)
            string += " $string_"
        } else {
            string += EMPTY_STRING_WITH_SPACE + currencyFromStr(vacancy.currency)
        }
        return string
    }

    private fun moneyFormat(num: Int) = "%,d".format(num).replace(",", EMPTY_STRING_WITH_SPACE)

    private fun currencyFromStr(name: String?): String {
        return Currency.valueOrNull(name.toString())?.abbr ?: name.toString()
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
    private fun changeLanguage(language: String = Locale.getDefault().displayLanguage) {
        when (language) {
            "English" -> {
                SALARY_NOT_SPECIFIED = "The salary is not specified"
                FROM = "from "
                TO = "to "
            }
            else -> {
                SALARY_NOT_SPECIFIED = "Зарплата не указана"
                FROM = "от "
                TO = "до "
            }
        }
    }
    private const val EMPTY_STRING = ""
    private const val EMPTY_STRING_WITH_SPACE = " "
    private var SALARY_NOT_SPECIFIED = "Зарплата не указана"
    private var FROM = "от "
    private var TO = "до "

}
