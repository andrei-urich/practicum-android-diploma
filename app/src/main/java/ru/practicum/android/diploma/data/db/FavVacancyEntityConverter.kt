package ru.practicum.android.diploma.data.db

class FavVacancyEntityConverter {
    fun map(favVacancyEntity: FavVacancyEntity?): String? {
        return if (favVacancyEntity != null) {
            /* 2DO заменить String на класс Vacancy */
            favVacancyEntity.name
        } else {
            null
        }
    }

    fun map(vacancy: String): FavVacancyEntity {
        /* 2DO заменить String на класс Vacancy */
        return FavVacancyEntity(vacancy, vacancy, vacancy, type = vacancy)
    }
}
