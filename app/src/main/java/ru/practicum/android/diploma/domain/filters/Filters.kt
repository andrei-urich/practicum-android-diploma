package ru.practicum.android.diploma.domain.filters

import ru.practicum.android.diploma.domain.filters.area.model.AreaFilterModel
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel

class Filters(
    private val area: AreaFilterModel = AreaFilterModel(),
    private val city: AreaFilterModel = AreaFilterModel(),
    private val industry: IndustryFilterModel = IndustryFilterModel(),
    private val salaryTarget: Int = EMPTY_SALARY_TARGET,
    private val salaryShowChecked: Boolean = false
) {
    fun isFiltersNotEmpty(): Boolean {
        val isNotEmpty = area.id != EMPTY_STRING ||
            city.id != EMPTY_STRING ||
            industry.id != EMPTY_STRING ||
            salaryTarget != EMPTY_SALARY_TARGET ||
            salaryShowChecked
        return isNotEmpty
    }

    fun getAreaNCityNames(): String {
        return if (area.id.isNotEmpty() && city.id.isNotEmpty()) {
            "${area.name}, ${city.name}"
        } else {
            area.name
        }
    }

    fun getAreaId(): String {
        return if (city.id == EMPTY_STRING) return area.id else city.id
    }

    fun getIndustryName(): String {
        return industry.name
    }
    fun getIndustryId(): String{
        return industry.id
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
        return this.area.id == other.area.id &&
            this.city.id == other.city.id &&
            this.industry.id == other.industry.id &&
            this.salaryTarget == other.salaryTarget &&
            this.salaryShowChecked == other.salaryShowChecked
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    companion object {
        private const val EMPTY_SALARY_TARGET: Int = -1
        private const val EMPTY_STRING = ""
        fun setNewAreaCity(oldFilter: Filters, newArea: AreaFilterModel, newCity: AreaFilterModel?): Filters {
            return Filters(
                newArea,
                newCity ?: AreaFilterModel(),
                oldFilter.industry,
                oldFilter.salaryTarget,
                oldFilter.salaryShowChecked
            )
        }

        fun setNewIndustry(oldFilter: Filters, newIndustry: IndustryFilterModel): Filters {
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
