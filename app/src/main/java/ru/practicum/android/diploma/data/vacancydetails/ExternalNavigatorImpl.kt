package ru.practicum.android.diploma.data.vacancydetails

import android.content.Intent
import ru.practicum.android.diploma.domain.vacancydetails.api.ExternalNavigator

class ExternalNavigatorImpl : ExternalNavigator {
    override fun shareVacancy(link: String): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
        shareIntent.putExtra(Intent.EXTRA_TEXT, link)
        return Intent.createChooser(shareIntent, "")
    }
}
