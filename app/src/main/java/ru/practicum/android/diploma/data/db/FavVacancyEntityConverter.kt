package ru.practicum.android.diploma.data.db

import ru.practicum.android.diploma.domain.models.Vacancy

class FavVacancyEntityConverter {
    fun map(favVacancyEntity: FavVacancyEntity): Vacancy {
        return Vacancy(favVacancyEntity.name)
    }

// 2DO Исправить конвертер после создания финальной версии Vacancy
    fun map(vacancy: Vacancy): FavVacancyEntity {
        return FavVacancyEntity(vacancy.name, vacancy.name, vacancy.name, type = vacancy.name)
    }
}
