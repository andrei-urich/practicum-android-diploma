package ru.practicum.android.diploma.domain.filters

class Filters(
    private val area: String = "",
    private val city: String = "",
    private val industry: String = "",
    private val salaryTarget: Int = EMPTY_SALARY_TARGET,
    private val salaryShowCheck: Boolean = false
) {
    fun isFiltersEmpty(): Boolean {
        return area.isNotEmpty() &&
            city.isNotEmpty() &&
            industry.isNotEmpty() &&
            salaryTarget != EMPTY_SALARY_TARGET &&
            !salaryShowCheck
    }


    companion object {
        const val EMPTY_SALARY_TARGET: Int = -1
        fun setNewAreaCity(oldFilter: Filters, newArea: String, newCity: String?): Filters {
            return Filters(
                newArea,
                newCity ?: "",
                oldFilter.industry,
                oldFilter.salaryTarget,
                oldFilter.salaryShowCheck
            )
        }

        fun setNewIndustry(oldFilter: Filters, newIndustry: String): Filters {
            return Filters(
                oldFilter.area,
                oldFilter.city,
                newIndustry,
                oldFilter.salaryTarget,
                oldFilter.salaryShowCheck
            )
        }

        fun setNewSalaryTarget(oldFilter: Filters, newTargetSalary: Int): Filters {
            return Filters(
                oldFilter.area,
                oldFilter.city,
                oldFilter.industry,
                newTargetSalary,
                oldFilter.salaryShowCheck
            )
        }

        fun setNewSalaryShowCheck(oldFilter: Filters, newSalaryShowCheck: Boolean): Filters {
            return Filters(
                oldFilter.area,
                oldFilter.city,
                oldFilter.industry,
                oldFilter.salaryTarget,
                newSalaryShowCheck
            )
        }
    }
}
