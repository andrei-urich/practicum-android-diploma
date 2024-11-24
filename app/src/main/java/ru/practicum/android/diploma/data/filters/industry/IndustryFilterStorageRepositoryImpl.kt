package ru.practicum.android.diploma.data.filters.industry

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel

class IndustryFilterStorageRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
) : IndustryFilterStorageRepository {

    override fun readIndustry(): IndustryFilterModel? {
        val json = sharedPreferences.getString(INDUSTRY_FILTERS_KEY, null)
        if (json != null) return gson.fromJson(json, IndustryFilterModel::class.java)
        return null
    }

    override fun writeIndustry(industry: IndustryFilterModel) {
        val json = gson.toJson(industry)
        sharedPreferences.edit()
            .putString(INDUSTRY_FILTERS_KEY, json)
            .apply()
    }

    override fun removeIndustry() {
        sharedPreferences.edit()
            .remove(INDUSTRY_FILTERS_KEY)
            .apply()
    }
    private companion object {
        const val INDUSTRY_FILTERS_KEY = "key_for_industry_filters"
    }
}
