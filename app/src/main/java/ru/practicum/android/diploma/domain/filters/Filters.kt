package ru.practicum.android.diploma.domain.filters

class Filters(
    private val area: String = EMPTY_STRING,
    private val city: String = EMPTY_STRING,
    private val industry: String = EMPTY_STRING,
    private val salaryTarget: Int = EMPTY_SALARY_TARGET,
    private val salaryShowChecked: Boolean = false
) {
    fun isFiltersNotEmpty(): Boolean {
        val isNotEmpty = area != EMPTY_STRING ||
            city != EMPTY_STRING ||
            industry != EMPTY_STRING ||
            salaryTarget != EMPTY_SALARY_TARGET ||
            salaryShowChecked
        return isNotEmpty
    }

    fun getAreaNCity(): String {
        return if (area.isNotEmpty() && city.isNotEmpty()) {
            "$area, $city"
        } else {
            area
        }
    }

    fun getIndustry(): String {
        return industry
    }

    fun getSalaryTarget(): String {
        return if (salaryTarget > 0) salaryTarget.toString() else EMPTY_STRING
    }

    fun getSalaryShowChecked(): Boolean {
        return salaryShowChecked
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Filters) return false
        return this.area == other.area &&
            this.city == other.city &&
            this.industry == other.industry &&
            this.salaryTarget == other.salaryTarget &&
            this.salaryShowChecked == other.salaryShowChecked
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    companion object {
        private const val EMPTY_SALARY_TARGET: Int = -1
        private const val EMPTY_STRING = ""
        fun setNewAreaCity(oldFilter: Filters, newArea: String, newCity: String?): Filters {
            return Filters(
                newArea,
                newCity ?: EMPTY_STRING,
                oldFilter.industry,
                oldFilter.salaryTarget,
                oldFilter.salaryShowChecked
            )
        }

        fun setNewIndustry(oldFilter: Filters, newIndustry: String): Filters {
            return Filters(
                oldFilter.area,
                oldFilter.city,
                newIndustry,
                oldFilter.salaryTarget,
                oldFilter.salaryShowChecked
            )
        }

        fun setNewSalaryTarget(oldFilter: Filters, newTargetSalary: Int): Filters {
            return Filters(
                oldFilter.area,
                oldFilter.city,
                oldFilter.industry,
                newTargetSalary,
                oldFilter.salaryShowChecked
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
